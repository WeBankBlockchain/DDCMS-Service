package com.webank.ddcms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import com.google.common.collect.Maps;
import com.webank.ddcms.bo.DataSchemaDetailBO;
import com.webank.ddcms.bo.DataSchemaWithAccessBO;
import com.webank.ddcms.bo.LoginUserBO;
import com.webank.ddcms.config.SysConfig;
import com.webank.ddcms.contracts.DataSchemaContract;
import com.webank.ddcms.dao.entity.*;
import com.webank.ddcms.dao.mapper.*;
import com.webank.ddcms.enums.AccountType;
import com.webank.ddcms.enums.CodeEnum;
import com.webank.ddcms.enums.ReviewItemType;
import com.webank.ddcms.enums.ReviewStatus;
import com.webank.ddcms.handler.ThreadLocalKeyPairHandler;
import com.webank.ddcms.service.DataSchemaService;
import com.webank.ddcms.utils.BlockchainUtils;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.PageListData;
import com.webank.ddcms.vo.request.dataschema.*;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataSchemaServiceImpl implements DataSchemaService {

  @Autowired private Client client;

  @Autowired private CryptoKeyPair witnessKeyPair;
  @Autowired private SysConfig sysConfig;

  @Autowired private AccountInfoMapper accountInfoMapper;

  @Autowired private ProductInfoMapper productInfoMapper;

  @Autowired private ThreadLocalKeyPairHandler keyPairHandler;

  @Autowired private TagInfoMapper tagInfoMapper;

  @Autowired private TransactionDecoderInterface txDecoder;

  @Autowired private DataSchemaInfoMapper dataSchemaInfoMapper;

  @Autowired private DataSchemaAccessInfoMapper dataSchemaAccessInfoMapper;

  @Autowired private DataSchemaTagsMapper dataSchemaTagsMapper;

  @Autowired private SchemaFavoriteInfoMapper schemaFavoriteInfoMapper;

  @Autowired private ReviewRecordInfoMapper reviewRecordInfoMapper;

  public CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String did = null;
    if (authentication != null
        && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      LoginUserBO bo = (LoginUserBO) authentication.getPrincipal();
      did = bo.getEntity().getDid();
    }
    int totalCount =
        dataSchemaInfoMapper.count(
            request.getProductId(),
            request.getProviderId(),
            request.getKeyWord(),
            null,
            request.getStatus(),
            request.getTagId());
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());
    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);
    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<DataSchemaDetailBO> dataSchemaDetailBOList =
        dataSchemaInfoMapper.pageQuerySchema(
            offset,
            request.getPageSize(),
            request.getProductId(),
            request.getProviderId(),
            request.getKeyWord(),
            request.getStatus(),
            request.getTagId());
    addTag(dataSchemaDetailBOList);
    addFav(did, dataSchemaDetailBOList);

    pageListData.setItemList(dataSchemaDetailBOList);
    return CommonResponse.success(pageListData);
  }

  private void addFav(String did, List<DataSchemaDetailBO> dataSchemaDetailBOList) {
    if (did == null) {
      return;
    }
    AccountInfoEntity accountInfoEntity = accountInfoMapper.selectByDid(did);
    List<Long> schemaIds =
        dataSchemaDetailBOList.stream()
            .filter(Objects::nonNull)
            .map(DataSchemaDetailBO::getSchemaId)
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(schemaIds)) {
      return;
    }
    List<SchemaFavoriteInfoEntity> schemaFavoriteInfoEntities =
        schemaFavoriteInfoMapper.getSchemaFavBySchemaIds(accountInfoEntity.getPkId(), schemaIds);
    Map<Long, SchemaFavoriteInfoEntity> favoriteInfoEntityMap =
        Maps.uniqueIndex(schemaFavoriteInfoEntities, SchemaFavoriteInfoEntity::getSchemaId);
    dataSchemaDetailBOList.forEach(
        dataSchemaDetailBO -> {
          dataSchemaDetailBO.setFavTag(
              favoriteInfoEntityMap.get(dataSchemaDetailBO.getSchemaId()) == null ? 0 : 1);
        });
  }

  @Override
  public CommonResponse pageQueryMySchema(PageQueryDataSchemaRequest request) {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String did = bo.getEntity().getDid();
    int totalCount =
        dataSchemaInfoMapper.count(
            null, null, request.getKeyWord(), did, request.getStatus(), null);
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());
    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);
    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<DataSchemaDetailBO> dataSchemaDetailBOList =
        dataSchemaInfoMapper.pageQueryMySchema(
            offset, request.getPageSize(), did, request.getKeyWord(), request.getStatus());
    addTag(dataSchemaDetailBOList);

    pageListData.setItemList(dataSchemaDetailBOList);
    return CommonResponse.success(pageListData);
  }

  @Override
  public CommonResponse pageQueryMyFavSchema(PageQueryMyFavSchemaRequest request) {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(bo.getEntity().getDid());
    int totalCount =
        schemaFavoriteInfoMapper.count(entity.getPkId(), request.getKeyWord(), request.getState());
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());
    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);
    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<DataSchemaDetailBO> dataSchemaDetailBOList =
        schemaFavoriteInfoMapper.pageQuerySchemaFavorite(
            offset,
            request.getPageSize(),
            entity.getPkId(),
            request.getKeyWord(),
            request.getState());
    addTag(dataSchemaDetailBOList);

    pageListData.setItemList(dataSchemaDetailBOList);
    return CommonResponse.success(pageListData);
  }

  @Override
  public CommonResponse addSchemaFavorite(CreateFavSchemaRequest request) {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(bo.getEntity().getDid());
    SchemaFavoriteInfoEntity schemaFavoriteInfoEntity = new SchemaFavoriteInfoEntity();
    schemaFavoriteInfoEntity.setSchemaId(request.getSchemaId());
    schemaFavoriteInfoEntity.setAccountId(entity.getPkId());
    schemaFavoriteInfoMapper.insertSchemaFavoriteInfo(schemaFavoriteInfoEntity);
    return CommonResponse.success(schemaFavoriteInfoEntity.getPkId());
  }

  @Override
  public CommonResponse delSchemaFavorite(DelFavSchemaRequest request) {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String did = bo.getEntity().getDid();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
    schemaFavoriteInfoMapper.delSchemaFavoriteInfo(request.getSchemaId(), entity.getPkId());
    return CommonResponse.success();
  }

  public CommonResponse getDataSchemaById(Long schemaId) {
    DataSchemaWithAccessBO dataSchemaWithAccessBO = dataSchemaInfoMapper.getSchemaById(schemaId);
    List<DataSchemaTagsEntity> schemaTagsEntityList =
        dataSchemaTagsMapper.getSchemaTagsMap(dataSchemaWithAccessBO.getSchemaId());
    if (CollectionUtil.isNotEmpty(schemaTagsEntityList)) {
      List<Long> tagIds =
          schemaTagsEntityList.stream()
              .filter(Objects::nonNull)
              .map(DataSchemaTagsEntity::getTagId)
              .collect(Collectors.toList());
      List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
      List<String> tagNames =
          tagInfoEntityList.stream()
              .filter(Objects::nonNull)
              .map(TagInfoEntity::getTagName)
              .collect(Collectors.toList());
      dataSchemaWithAccessBO.setTagNameList(tagNames);
    }
    return CommonResponse.success(dataSchemaWithAccessBO);
  }

  public CommonResponse getDataSchemaAccessById(Long accessId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String did = null;
    if (authentication != null
        && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      LoginUserBO bo = (LoginUserBO) authentication.getPrincipal();
      did = bo.getEntity().getDid();
    }
    AccountInfoEntity entity = null;
    if (did != null) {
      entity = accountInfoMapper.selectByDid(did);
    }
    DataSchemaWithAccessBO dataSchemaWithAccessBO =
        dataSchemaAccessInfoMapper.getSchemaAccessById(accessId);
    DataSchemaInfoEntity dataSchemaInfoEntity =
        dataSchemaInfoMapper.getSchemaBySchemaId(dataSchemaWithAccessBO.getSchemaId());
    if (dataSchemaInfoEntity.getVisible() == 1) {
      return CommonResponse.success(dataSchemaWithAccessBO);
    }
    if (entity != null) {
      if (entity.getAccountType() == AccountType.ADMIN.getRoleKey()
          || entity.getAccountType() == AccountType.WITNESS.getRoleKey()
          || entity.getPkId().longValue() == dataSchemaInfoEntity.getProviderId().longValue()) {
        return CommonResponse.success(dataSchemaWithAccessBO);
      }
    }
    return CommonResponse.success(null);
  }

  @Override
  public CommonResponse approveDataSchema(ApproveDataSchemaRequest request)
      throws TransactionException {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String did = bo.getEntity().getDid();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(did);

    DataSchemaInfoEntity dataSchemaInfoEntity =
        dataSchemaInfoMapper.getSchemaBySchemaId(request.getSchemaId());
    if (dataSchemaInfoEntity == null) {
      return CommonResponse.error(CodeEnum.SCHEMA_NOT_EXISTS);
    }
    String privateKey = entity.getPrivateKey();
    CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
    CryptoKeyPair witnessKeyPair = cryptoSuite.loadKeyPair(privateKey);
    DataSchemaContract schemaContract =
        DataSchemaContract.load(
            sysConfig.getContractConfig().getDataSchemaContract(), client, witnessKeyPair);
    TransactionReceipt receipt =
        schemaContract.approveDataSchema(
            HexUtil.decodeHex(dataSchemaInfoEntity.getDataSchemaBid()), request.isAgree());
    BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);
    int reviewState = schemaContract.getApproveDataSchemaOutput(receipt).getValue4().intValue();
    int agreeCount = schemaContract.getApproveDataSchemaOutput(receipt).getValue2().intValue();
    int denyCount = schemaContract.getApproveDataSchemaOutput(receipt).getValue3().intValue();

    dataSchemaInfoMapper.updateDataSchemaState(request.getSchemaId(), reviewState);

    ReviewRecordInfoEntity reviewRecordInfoEntity = new ReviewRecordInfoEntity();
    reviewRecordInfoEntity.setItemType(ReviewItemType.Schema.getCode());
    reviewRecordInfoEntity.setItemId(request.getSchemaId());
    reviewRecordInfoEntity.setAgreeCount(agreeCount);
    reviewRecordInfoEntity.setDenyCount(denyCount);
    reviewRecordInfoEntity.setReviewState(reviewState);
    reviewRecordInfoMapper.updateDataSchemaInfo(reviewRecordInfoEntity);
    return CommonResponse.success(dataSchemaInfoEntity.getPkId());
  }

  @Transactional(rollbackFor = Exception.class)
  public CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception {
    LoginUserBO bo =
        (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
    AccountInfoEntity entity = accountInfoMapper.selectByDid(bo.getEntity().getDid());
    ProductInfoEntity product =
        productInfoMapper.getProductByProductId(schemaRequest.getProductId());
    if (product == null) {
      return CommonResponse.error(CodeEnum.PRODUCT_NOT_EXISTS);
    }
    String privateKey = entity.getPrivateKey();
    CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
    DataSchemaContract dataSchemaModule =
        DataSchemaContract.load(
            sysConfig.getContractConfig().getDataSchemaContract(), client, keyPair);

    byte[] hash =
        cryptoSuite.hash(
            (schemaRequest.getProductId()
                    + schemaRequest.getContentSchema()
                    + schemaRequest.getDataSchemaName())
                .getBytes(StandardCharsets.UTF_8));

    TransactionReceipt receipt =
        dataSchemaModule.createDataSchema(
            hash, HexUtil.decodeHex(product.getProductBid())); // TODO:还需要传入product的bid
    BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

    String dataSchemaId =
        HexUtil.encodeHexStr(dataSchemaModule.getCreateDataSchemaOutput(receipt).getValue1());
    int witnessCount = dataSchemaModule.getCreateDataSchemaOutput(receipt).getValue2().intValue();

    DataSchemaInfoEntity dataSchemaInfoEntity = new DataSchemaInfoEntity();
    BeanUtils.copyProperties(schemaRequest, dataSchemaInfoEntity);
    dataSchemaInfoEntity.setDataSchemaBid(dataSchemaId);
    dataSchemaInfoEntity.setProviderId(entity.getPkId());
    dataSchemaInfoEntity.setStatus(ReviewStatus.NotReviewed.ordinal());
    dataSchemaInfoEntity.setReviewTime(new Timestamp(System.currentTimeMillis()));
    dataSchemaInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    dataSchemaInfoMapper.insertDataSchemaInfo(dataSchemaInfoEntity);
    log.info("save dataSchemaInfoEntity finish, schemaId = {}", dataSchemaId);

    DataSchemaAccessInfoEntity dataSchemaAccessInfoEntity = new DataSchemaAccessInfoEntity();
    BeanUtils.copyProperties(schemaRequest, dataSchemaAccessInfoEntity);
    dataSchemaAccessInfoEntity.setDataSchemaId(dataSchemaInfoEntity.getPkId());
    dataSchemaAccessInfoEntity.setCreateTime(new Date());
    dataSchemaAccessInfoMapper.insertDataSchemaAccessInfo(dataSchemaAccessInfoEntity);
    log.info("save dataSchemaAccessInfoEntity finish, schemaId = {}", dataSchemaId);

    ReviewRecordInfoEntity reviewRecordInfoEntity = new ReviewRecordInfoEntity();
    reviewRecordInfoEntity.setWitnessCount(witnessCount);
    reviewRecordInfoEntity.setItemId(dataSchemaInfoEntity.getPkId());
    reviewRecordInfoEntity.setItemType(ReviewItemType.Schema.getCode());
    reviewRecordInfoMapper.insertReviewRecordInfo(reviewRecordInfoEntity);

    List<String> tagNames = schemaRequest.getTagNameList();
    tagNames.forEach(
        tagName -> {
          TagInfoEntity tagInfo = tagInfoMapper.queryTagByName(tagName);
          if (tagInfo == null) {
            tagInfo = new TagInfoEntity();
            tagInfo.setTagName(tagName);
            tagInfoMapper.insertItem(tagInfo);
          }
          DataSchemaTagsEntity dataSchemaTagsEntity = new DataSchemaTagsEntity();
          dataSchemaTagsEntity.setDataSchemaId(dataSchemaInfoEntity.getPkId());
          dataSchemaTagsEntity.setTagId(tagInfo.getPkId());
          dataSchemaTagsMapper.insertDataSchemaTag(dataSchemaTagsEntity);
        });
    log.info("save dataSchemaTagsEntity finish, schemaId = {}", dataSchemaId);

    return CommonResponse.success(dataSchemaId);
  }

  private void handleTag(UpdateDataSchemaRequest schemaRequest) {
    List<String> tagNames = schemaRequest.getTagNameList();
    List<DataSchemaTagsEntity> dataSchemaTagsEntityList =
        dataSchemaTagsMapper.getSchemaTagsMap(schemaRequest.getSchemaId());
    List<Long> tagIds =
        dataSchemaTagsEntityList.stream()
            .map(DataSchemaTagsEntity::getTagId)
            .distinct()
            .collect(Collectors.toList());
    List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
    List<String> tagNameList =
        tagInfoEntityList.stream()
            .map(TagInfoEntity::getTagName)
            .distinct()
            .collect(Collectors.toList());

    List<String> addTags =
        tagNames.stream()
            .filter(tagName -> !tagNameList.contains(tagName))
            .collect(Collectors.toList());

    addTags.forEach(
        tagName -> {
          TagInfoEntity tagInfo = tagInfoMapper.queryTagByName(tagName);
          if (tagInfo == null) {
            tagInfo = new TagInfoEntity();
            tagInfo.setTagName(tagName);
            tagInfoMapper.insertItem(tagInfo);
          }
          DataSchemaTagsEntity dataSchemaTagsEntity = new DataSchemaTagsEntity();
          dataSchemaTagsEntity.setDataSchemaId(schemaRequest.getSchemaId());
          dataSchemaTagsEntity.setTagId(tagInfo.getPkId());
          dataSchemaTagsMapper.insertDataSchemaTag(dataSchemaTagsEntity);
        });

    List<String> delTags =
        tagNameList.stream().filter(str -> !tagNames.contains(str)).collect(Collectors.toList());

    List<Long> delTagIds = tagInfoMapper.getTagIdsByNames(delTags);
    dataSchemaTagsMapper.delDataSchemaTag(delTagIds, schemaRequest.getSchemaId());
  }

  private void addTag(List<DataSchemaDetailBO> dataSchemaDetailBOList) {
    if (CollectionUtil.isNotEmpty(dataSchemaDetailBOList)) {
      List<Long> schemaIds =
          dataSchemaDetailBOList.stream()
              .filter(Objects::nonNull)
              .map(DataSchemaDetailBO::getSchemaId)
              .collect(Collectors.toList());
      List<DataSchemaTagsEntity> schemaTagsEntityList =
          dataSchemaTagsMapper.getSchemaTagsMapByIds(schemaIds);
      if (CollectionUtil.isNotEmpty(schemaTagsEntityList)) {
        List<Long> tagIds =
            schemaTagsEntityList.stream()
                .map(DataSchemaTagsEntity::getTagId)
                .distinct()
                .collect(Collectors.toList());
        List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
        Map<Long, String> tagIdNameMap =
            tagInfoEntityList.stream()
                .collect(Collectors.toMap(TagInfoEntity::getPkId, TagInfoEntity::getTagName));

        Map<Long, List<DataSchemaTagsEntity>> schemaIdMap =
            schemaTagsEntityList.stream()
                .collect(Collectors.groupingBy(DataSchemaTagsEntity::getDataSchemaId));

        dataSchemaDetailBOList.forEach(
            dataSchemaDetailBO -> {
              List<DataSchemaTagsEntity> dataSchemaTagsEntities =
                  schemaIdMap.get(dataSchemaDetailBO.getSchemaId());
              List<String> tagNames = new ArrayList<>();
              dataSchemaTagsEntities.forEach(
                  dataSchemaTagsEntity -> {
                    tagNames.add(tagIdNameMap.get(dataSchemaTagsEntity.getTagId()));
                  });
              dataSchemaDetailBO.setTagNameList(tagNames);
            });
      }
    }
  }
}
