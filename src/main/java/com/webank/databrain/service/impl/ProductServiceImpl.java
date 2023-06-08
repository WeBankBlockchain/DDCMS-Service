package com.webank.databrain.service.impl;

import cn.hutool.core.util.HexUtil;
import com.webank.databrain.bo.HotProductBO;
import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.bo.ProductInfoBO;
import com.webank.databrain.config.SysConfig;
import com.webank.databrain.contracts.ProductContract;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.entity.ProductInfoEntity;
import com.webank.databrain.dao.entity.ReviewRecordInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import com.webank.databrain.dao.mapper.ProductInfoMapper;
import com.webank.databrain.dao.mapper.ReviewRecordInfoMapper;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.enums.ReviewItemType;
import com.webank.databrain.enums.ReviewStatus;
import com.webank.databrain.handler.ThreadLocalKeyPairHandler;
import com.webank.databrain.service.ProductService;
import com.webank.databrain.utils.BlockchainUtils;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.common.PageListData;
import com.webank.databrain.vo.request.product.ApproveProductRequest;
import com.webank.databrain.vo.request.product.CreateProductRequest;
import com.webank.databrain.vo.request.product.PageQueryProductRequest;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired private Client client;

  @Autowired private ThreadLocalKeyPairHandler keyPairHandler;

  @Autowired private CryptoKeyPair witnessKeyPair;
  @Autowired private SysConfig sysConfig;

  @Autowired private TransactionDecoderInterface txDecoder;

  @Autowired private AccountInfoMapper accountInfoMapper;

  @Autowired private ProductInfoMapper productInfoMapper;

  @Autowired private ReviewRecordInfoMapper reviewRecordInfoMapper;

  public CommonResponse getHotProducts(HotDataRequest request) {
    List<HotProductBO> productInfoEntities = productInfoMapper.getHotProduct(request.getTopCount());
    return CommonResponse.success(productInfoEntities);
  }

  public CommonResponse pageQueryProducts(PageQueryProductRequest request) {

    int totalCount =
        productInfoMapper.count(
            null, request.getKeyWord(), request.getStatus(), request.getProviderId());
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);

    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<ProductInfoBO> productInfoPOList =
        productInfoMapper.pageQueryProduct(
            offset,
            request.getPageSize(),
            request.getKeyWord(),
            request.getStatus(),
            request.getProviderId());

    pageListData.setItemList(productInfoPOList);
    return CommonResponse.success(pageListData);
  }

  public CommonResponse getProductDetail(Long productId) {
    ProductInfoBO product = productInfoMapper.getProductById(productId);
    return CommonResponse.success(product);
  }

  @Transactional(rollbackFor = Exception.class)
  public CommonResponse createProduct(CreateProductRequest productRequest)
      throws TransactionException {
    CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(bo.getEntity().getDid());

    String privateKey = entity.getPrivateKey();
    CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
    ProductContract productModule =
        ProductContract.load(sysConfig.getContractConfig().getProductContract(), client, keyPair);

    TransactionReceipt receipt =
        productModule.createProduct(
            cryptoSuite.hash(
                (productRequest.getProductName() + productRequest.getProductDesc())
                    .getBytes(StandardCharsets.UTF_8)));
    BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

    String productId =
        HexUtil.encodeHexStr(productModule.getCreateProductOutput(receipt).getValue1());
    int witnessCount = productModule.getCreateProductOutput(receipt).getValue2().intValue();

    ProductInfoEntity product = new ProductInfoEntity();
    product.setProductBid(productId);
    product.setProviderId(entity.getPkId());
    product.setStatus(ReviewStatus.NotReviewed.ordinal());
    product.setProductName(productRequest.getProductName());
    product.setProductDesc(productRequest.getProductDesc());
    productInfoMapper.insertProductInfo(product);

    ReviewRecordInfoEntity reviewRecordInfoEntity = new ReviewRecordInfoEntity();
    reviewRecordInfoEntity.setReviewState(ReviewStatus.NotReviewed.ordinal());
    reviewRecordInfoEntity.setWitnessCount(witnessCount);
    reviewRecordInfoEntity.setItemId(product.getPkId());
    reviewRecordInfoEntity.setItemType(ReviewItemType.Product.getCode());
    reviewRecordInfoMapper.insertReviewRecordInfo(reviewRecordInfoEntity);

    return CommonResponse.success(productId);
  }

  @Transactional(rollbackFor = Exception.class)
  public CommonResponse approveProduct(ApproveProductRequest productRequest)
      throws TransactionException {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String did = bo.getEntity().getDid();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
    if (entity == null) {
      return CommonResponse.error(CodeEnum.USER_NOT_EXISTS);
    }
    ProductInfoEntity productInfoEntity =
        productInfoMapper.getProductByProductId(productRequest.getProductId());
    if (productInfoEntity == null) {
      return CommonResponse.error(CodeEnum.PRODUCT_NOT_EXISTS);
    }
    String privateKey = entity.getPrivateKey();
    CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
    CryptoKeyPair witnessKeyPair = cryptoSuite.loadKeyPair(privateKey);
    ProductContract productModule =
        ProductContract.load(
            sysConfig.getContractConfig().getProductContract(), client, witnessKeyPair);
    TransactionReceipt receipt =
        productModule.approveProduct(
            HexUtil.decodeHex(productInfoEntity.getProductBid()), productRequest.isAgree());
    BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);
    int reviewState = productModule.getApproveProductOutput(receipt).getValue4().intValue();
    int agreeCount = productModule.getApproveProductOutput(receipt).getValue2().intValue();
    int denyCount = productModule.getApproveProductOutput(receipt).getValue3().intValue();

    ProductInfoEntity productInfoEntityUp = new ProductInfoEntity();
    productInfoEntityUp.setPkId(productRequest.getProductId());
    productInfoEntityUp.setStatus(reviewState);
    productInfoMapper.updateProductInfoState(productInfoEntityUp);

    ReviewRecordInfoEntity reviewRecordInfoEntity = new ReviewRecordInfoEntity();
    reviewRecordInfoEntity.setItemType(ReviewItemType.Product.getCode());
    reviewRecordInfoEntity.setItemId(productRequest.getProductId());
    reviewRecordInfoEntity.setAgreeCount(agreeCount);
    reviewRecordInfoEntity.setDenyCount(denyCount);
    reviewRecordInfoEntity.setReviewState(reviewState);
    reviewRecordInfoMapper.updateDataSchemaInfo(reviewRecordInfoEntity);

    return CommonResponse.success(productInfoEntity.getPkId());
  }

  @Override
  public CommonResponse pageQueryMyProduct(PageQueryProductRequest request) {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String did = bo.getEntity().getDid();

    int totalCount = productInfoMapper.count(did, request.getKeyWord(), request.getStatus(), null);
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);

    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<ProductInfoBO> productInfoPOList =
        productInfoMapper.pageQueryMyProduct(
            offset, request.getPageSize(), request.getKeyWord(), did, request.getStatus());

    pageListData.setItemList(productInfoPOList);
    return CommonResponse.success(pageListData);
  }

  @Override
  public CommonResponse getProductsByProviderId() {

    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    long providerId = bo.getEntity().getPkId();

    List<ProductInfoEntity> entityList = productInfoMapper.getProductsByProviderId(providerId);
    return CommonResponse.success(entityList);
  }
}
