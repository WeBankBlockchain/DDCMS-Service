package com.webank.databrain.service.impl;

import cn.hutool.core.util.HexUtil;
import com.webank.databrain.bo.HotProductBO;
import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.bo.ProductInfoBO;
import com.webank.databrain.config.SysConfig;
import com.webank.databrain.dao.bc.contract.ProductModule;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.entity.ProductInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import com.webank.databrain.dao.mapper.ProductInfoMapper;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.enums.ReviewStatus;
import com.webank.databrain.handler.ThreadLocalKeyPairHandler;
import com.webank.databrain.service.ProductService;
import com.webank.databrain.utils.BlockchainUtils;
import com.webank.databrain.vo.common.CommonPageQueryRequest;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.common.PageListData;
import com.webank.databrain.vo.request.product.ApproveProductRequest;
import com.webank.databrain.vo.request.product.CreateProductRequest;
import com.webank.databrain.vo.request.product.UpdateProductRequest;
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

    @Autowired
    private Client client;

    @Autowired
    private ThreadLocalKeyPairHandler keyPairHandler;

    @Autowired
    private CryptoKeyPair witnessKeyPair;
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private TransactionDecoderInterface txDecoder;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;



    public CommonResponse getHotProducts(HotDataRequest request) {
        List<HotProductBO> productInfoEntities = productInfoMapper.getHotProduct(request.getTopCount());
        return CommonResponse.success(productInfoEntities);
    }

    public CommonResponse pageQueryProducts(CommonPageQueryRequest request) {

        int totalCount = productInfoMapper.count();
        int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

        PageListData pageListData = new PageListData<>();
        pageListData.setPageCount(pageCount);
        pageListData.setTotalCount(totalCount);

        int offset = (request.getPageNo() - 1) * request.getPageSize();


        List<ProductInfoBO> productInfoPOList = productInfoMapper.pageQueryProduct(
                offset,
                request.getPageSize());

        pageListData.setItemList(productInfoPOList);
        return CommonResponse.success(pageListData);
    }

    public CommonResponse getProductDetail(String productGid) {
        ProductInfoBO product = productInfoMapper.getProductByGId(productGid);
        return CommonResponse.success(product);
    }

    public CommonResponse getProductDetail(Long productId) {
        ProductInfoBO product = productInfoMapper.getProductById(productId);
        return CommonResponse.success(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse createProduct(CreateProductRequest productRequest) throws TransactionException {
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        LoginUserBO bo = (LoginUserBO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountInfoEntity entity = accountInfoMapper.selectByDid(bo.getEntity().getDid());

        String privateKey = entity.getPrivateKey();
        CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
        ProductModule productModule = ProductModule.load(
                sysConfig.getContractConfig().getProductContract(),
                client,
                keyPair);

        TransactionReceipt receipt = productModule.createProduct(cryptoSuite.hash((
                productRequest.getProductName() + productRequest.getProductDesc())
                .getBytes(StandardCharsets.UTF_8)));
        BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

        String productId = HexUtil.encodeHexStr(productModule.getCreateProductOutput(receipt).getValue1());
        ProductInfoEntity product = new ProductInfoEntity();
        product.setProductGid(productId);
        product.setProviderId(entity.getPkId());
        product.setStatus(ReviewStatus.NotReviewed.ordinal());
        product.setProductName(productRequest.getProductName());
        product.setProductDesc(productRequest.getProductDesc());
        productInfoMapper.insertProductInfo(product);
        return CommonResponse.success(productId);
    }
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse updateProduct(UpdateProductRequest productRequest) throws TransactionException {
        String did = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountInfoEntity entity = accountInfoMapper.selectByDid(did);

        ProductInfoBO productInfoBO = productInfoMapper.getProductById(productRequest.getProductId());
        if (productInfoBO == null) {
            return CommonResponse.error(CodeEnum.PRODUCT_NOT_EXISTS);
        }
        String privateKey = entity.getPrivateKey();
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
        ProductModule productModule = ProductModule.load(
                sysConfig.getContractConfig().getAccountContract(),
                client,
                keyPair);

        TransactionReceipt receipt = productModule.modifyProduct(
<<<<<<< HEAD
                Base64.decode(productInfoBO.getProductGid()),
=======
                HexUtil.decodeHex(productInfoBO.getProductGid()),
>>>>>>> 72ef17d63c511280540fca48aeba387490d622ed
                cryptoSuite.hash((
                        productRequest.getProductName() + productRequest.getProductDesc())
                        .getBytes(StandardCharsets.UTF_8)));
        BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

        ProductInfoEntity product = new ProductInfoEntity();
        product.setPkId(productRequest.getProductId());
        product.setProductName(productRequest.getProductName());
        product.setProductDesc(productRequest.getProductDesc());
        productInfoMapper.updateProductInfo(product);

        return CommonResponse.success(productInfoBO.getProductGid());
    }


    @Transactional(rollbackFor = Exception.class)
    public CommonResponse approveProduct(ApproveProductRequest productRequest) throws TransactionException {
        String did = SecurityContextHolder.getContext().getAuthentication().getName();

        AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
        if (entity == null) {
            return CommonResponse.error(CodeEnum.USER_NOT_EXISTS);
        }
        ProductInfoBO productInfoBO = productInfoMapper.getProductById(productRequest.getProductId());
        if (productInfoBO == null) {
            return CommonResponse.error(CodeEnum.PRODUCT_NOT_EXISTS);
        }
        CryptoKeyPair witnessKeyPair = this.witnessKeyPair;
        ProductModule productModule = ProductModule.load(
                sysConfig.getContractConfig().getProductContract(),
                client,
                witnessKeyPair);
        TransactionReceipt receipt = productModule.approveProduct(
<<<<<<< HEAD
                Base64.decode(productInfoBO.getProductGid()), productRequest.isAgree()
=======
                HexUtil.decodeHex(productInfoBO.getProductGid()), productRequest.isAgree()
>>>>>>> 72ef17d63c511280540fca48aeba387490d622ed
        );
        BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

        ProductInfoEntity productInfoEntity = new ProductInfoEntity();
        productInfoEntity.setProductGid(productInfoBO.getProductGid());
        productInfoEntity.setPkId(productRequest.getProductId());
        productInfoEntity.setStatus(productRequest.isAgree() ? ReviewStatus.Approved.ordinal() : ReviewStatus.Denied.ordinal());
        // 不需要set
        // productInfoEntity.setReviewTime(new Date());
        productInfoMapper.updateProductInfoState(productInfoEntity);
        return CommonResponse.success(productInfoBO.getProductGid());
    }
}
