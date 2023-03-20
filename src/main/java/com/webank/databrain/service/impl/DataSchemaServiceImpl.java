package com.webank.databrain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import com.webank.databrain.bo.DataSchemaDetailBO;
import com.webank.databrain.bo.DataSchemaWithAccessBO;
import com.webank.databrain.bo.ProductInfoBO;
import com.webank.databrain.config.SysConfig;
import com.webank.databrain.dao.bc.contract.DataSchemaModule;
import com.webank.databrain.dao.entity.*;
import com.webank.databrain.dao.mapper.*;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.handler.ThreadLocalKeyPairHandler;
import com.webank.databrain.service.DataSchemaService;
import com.webank.databrain.utils.BlockchainUtils;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.PageListData;
import com.webank.databrain.vo.request.dataschema.CreateDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.PageQueryDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.UpdateDataSchemaRequest;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataSchemaServiceImpl implements DataSchemaService {

    @Autowired
    private Client client;


    @Autowired
    private CryptoKeyPair witnessKeyPair;
    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ThreadLocalKeyPairHandler keyPairHandler;

    @Autowired
    private TagInfoMapper tagInfoMapper;

    @Autowired
    private TransactionDecoderInterface txDecoder;

    @Autowired
    private DataSchemaInfoMapper dataSchemaInfoMapper;

    @Autowired
    private DataSchemaAccessInfoMapper dataSchemaAccessInfoMapper;

    @Autowired
    private DataSchemaTagsMapper dataSchemaTagsMapper;


    public CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request) {
        int totalCount = dataSchemaInfoMapper.count();

        int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

        PageListData pageListData = new PageListData<>();
        pageListData.setPageCount(pageCount);
        pageListData.setTotalCount(totalCount);

        int offset = (request.getPageNo() - 1) * request.getPageSize();

        List<DataSchemaDetailBO> dataSchemaDetailBOList = dataSchemaInfoMapper.pageQuerySchema(
                offset,
                request.getPageSize(),
                request.getProductId(),
                request.getProviderId(),
                request.getKeyWord());
        if(CollectionUtil.isNotEmpty(dataSchemaDetailBOList)) {
            List<Long> schemaIds = dataSchemaDetailBOList.stream()
                    .filter(Objects::nonNull)
                    .map(DataSchemaDetailBO::getSchemaId)
                    .collect(Collectors.toList());
            List<DataSchemaTagsEntity> schemaTagsEntityList = dataSchemaTagsMapper.getSchemaTagsMapByIds(schemaIds);
            if(CollectionUtil.isNotEmpty(schemaTagsEntityList)) {
                List<Long> tagIds = schemaTagsEntityList.stream()
                        .map(DataSchemaTagsEntity::getTagId)
                        .distinct()
                        .collect(Collectors.toList());
                List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
                Map<Long, String> tagIdNameMap = tagInfoEntityList.stream()
                        .collect(Collectors.toMap(TagInfoEntity::getPkId, TagInfoEntity::getTagName));

                Map<Long, List<DataSchemaTagsEntity>> schemaIdMap = schemaTagsEntityList.stream()
                        .collect(Collectors.groupingBy(DataSchemaTagsEntity::getDataSchemaId));

                dataSchemaDetailBOList.forEach(dataSchemaDetailBO -> {
                    List<DataSchemaTagsEntity> dataSchemaTagsEntities = schemaIdMap.get(dataSchemaDetailBO.getSchemaId());
                    List<String> tagNames = new ArrayList<>();
                    dataSchemaTagsEntities.forEach(dataSchemaTagsEntity -> {
                        tagNames.add(tagIdNameMap.get(dataSchemaTagsEntity.getTagId()));
                    });
                    dataSchemaDetailBO.setTagNameList(tagNames);
                });
            }
        }

        pageListData.setItemList(dataSchemaDetailBOList);
        return CommonResponse.success(pageListData);
    }

    public CommonResponse getDataSchemaByGid(String schemaGid){
        DataSchemaWithAccessBO dataSchemaWithAccessResponse = dataSchemaInfoMapper.getSchemaByGid(schemaGid);
        List<DataSchemaTagsEntity> schemaTagsEntityList = dataSchemaTagsMapper.getSchemaTagsMap(dataSchemaWithAccessResponse.getSchemaId());
        if (CollectionUtil.isNotEmpty(schemaTagsEntityList)){
            List<Long> tagIds = schemaTagsEntityList.stream()
                    .filter(Objects::nonNull)
                    .map(DataSchemaTagsEntity::getTagId)
                    .collect(Collectors.toList());
            List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
            List<String> tagNames = tagInfoEntityList.stream()
                    .filter(Objects::nonNull)
                    .map(TagInfoEntity::getTagName)
                    .collect(Collectors.toList());
            dataSchemaWithAccessResponse.setTagNameList(tagNames);
        }
        return CommonResponse.success(dataSchemaWithAccessResponse);
    }

    public CommonResponse getDataSchemaByGid(Long schemaId){
        DataSchemaWithAccessBO dataSchemaWithAccessBO = dataSchemaInfoMapper.getSchemaById(schemaId);
        List<DataSchemaTagsEntity> schemaTagsEntityList = dataSchemaTagsMapper.getSchemaTagsMap(dataSchemaWithAccessBO.getSchemaId());
        if (CollectionUtil.isNotEmpty(schemaTagsEntityList)){
            List<Long> tagIds = schemaTagsEntityList.stream()
                    .filter(Objects::nonNull)
                    .map(DataSchemaTagsEntity::getTagId)
                    .collect(Collectors.toList());
            List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
            List<String> tagNames = tagInfoEntityList.stream()
                    .filter(Objects::nonNull)
                    .map(TagInfoEntity::getTagName)
                    .collect(Collectors.toList());
            dataSchemaWithAccessBO.setTagNameList(tagNames);
        }
        return CommonResponse.success(dataSchemaWithAccessBO);
    }

    public CommonResponse getDataSchemaAccessById(Long accessId){
        DataSchemaWithAccessBO dataSchemaWithAccessBO = dataSchemaAccessInfoMapper.getSchemaAccessByGid(accessId);
        return CommonResponse.success(dataSchemaWithAccessBO);
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse updateDataSchema(UpdateDataSchemaRequest schemaRequest) throws TransactionException {
        String did = SecurityContextHolder.getContext().getAuthentication().getName();
        DataSchemaWithAccessBO dataSchemaWithAccessBO = dataSchemaInfoMapper.getSchemaById(schemaRequest.getSchemaId());
        if (dataSchemaWithAccessBO == null){
            return CommonResponse.error(CodeEnum.SCHEMA_NOT_EXISTS);
        }
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
        String privateKey = entity.getPrivateKey();
        CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
        DataSchemaModule dataSchemaModule = DataSchemaModule.load(
                sysConfig.getContractConfig().getDataSchemaContract(),
                client,
                keyPair);

        byte[] hash = cryptoSuite.hash((dataSchemaWithAccessBO.getProductId() +
                schemaRequest.getContentSchema() +
                schemaRequest.getDataSchemaName())
                .getBytes(StandardCharsets.UTF_8));

        byte[] dataSchemaId = HexUtil.decodeHex(dataSchemaWithAccessBO.getDataSchemaGid());

        TransactionReceipt receipt = dataSchemaModule.modifyDataSchema(dataSchemaId,hash);
        BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

        DataSchemaInfoEntity dataSchemaInfoEntityUp = new DataSchemaInfoEntity();
        BeanUtils.copyProperties(schemaRequest, dataSchemaInfoEntityUp);
        dataSchemaInfoEntityUp.setDataSchemaGid(dataSchemaWithAccessBO.getDataSchemaGid());
        dataSchemaInfoEntityUp.setPkId(schemaRequest.getSchemaId());
        dataSchemaInfoMapper.updateDataSchemaInfo(dataSchemaInfoEntityUp);
        log.info("save dataSchemaInfoEntity finish, schemaId = {}", dataSchemaId);

        DataSchemaAccessInfoEntity dataSchemaAccessInfoEntity = new DataSchemaAccessInfoEntity();
        BeanUtils.copyProperties(schemaRequest, dataSchemaAccessInfoEntity);
        dataSchemaAccessInfoEntity.setDataSchemaId(dataSchemaWithAccessBO.getSchemaId());
        dataSchemaAccessInfoMapper.updateDataSchemaAccessInfo(dataSchemaAccessInfoEntity);
        log.info("save dataSchemaAccessInfoEntity finish, schemaId = {}", dataSchemaId);

        //处理标签，从更新的标签中取出不一样的，进行更新，并进行删除
        handlerTag(schemaRequest);
        log.info("handlerTag finish, schemaId = {}", dataSchemaId);

        return CommonResponse.success(dataSchemaId);
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception {
        String did = SecurityContextHolder.getContext().getAuthentication().getName();
        CryptoSuite cryptoSuite = keyPairHandler.getCryptoSuite();
        AccountInfoEntity entity = accountInfoMapper.selectByDid(did);
        ProductInfoBO product = productInfoMapper.getProductById(schemaRequest.getProductId());
        if(product == null){
            return CommonResponse.error(CodeEnum.PRODUCT_NOT_EXISTS);
        }
        String privateKey = entity.getPrivateKey();
        CryptoKeyPair keyPair = cryptoSuite.loadKeyPair(privateKey);
        DataSchemaModule dataSchemaModule = DataSchemaModule.load(
                sysConfig.getContractConfig().getDataSchemaContract(),
                client,
                keyPair);

        byte[] hash = cryptoSuite.hash((schemaRequest.getProductId() +
                schemaRequest.getContentSchema() +
                schemaRequest.getDataSchemaName())
                .getBytes(StandardCharsets.UTF_8));

        TransactionReceipt receipt = dataSchemaModule.createDataSchema(hash);
        BlockchainUtils.ensureTransactionSuccess(receipt, txDecoder);

        String dataSchemaId = HexUtil.encodeHexStr(dataSchemaModule.getCreateDataSchemaOutput(receipt).getValue1());
        DataSchemaInfoEntity dataSchemaInfoEntity = new DataSchemaInfoEntity();
        BeanUtils.copyProperties(schemaRequest, dataSchemaInfoEntity);
        dataSchemaInfoEntity.setDataSchemaGid(dataSchemaId);
        dataSchemaInfoEntity.setProviderId(entity.getPkId());
        dataSchemaInfoMapper.insertDataSchemaInfo(dataSchemaInfoEntity);
        log.info("save dataSchemaInfoEntity finish, schemaId = {}", dataSchemaId);

        DataSchemaAccessInfoEntity dataSchemaAccessInfoEntity = new DataSchemaAccessInfoEntity();
        BeanUtils.copyProperties(schemaRequest, dataSchemaAccessInfoEntity);
        dataSchemaAccessInfoEntity.setDataSchemaId(dataSchemaInfoEntity.getPkId());
        dataSchemaAccessInfoMapper.insertDataSchemaAccessInfo(dataSchemaAccessInfoEntity);
        log.info("save dataSchemaAccessInfoEntity finish, schemaId = {}", dataSchemaId);

        List<String> tagNames = schemaRequest.getTagNameList();
        tagNames.forEach(tagName -> {
            TagInfoEntity tagInfo = tagInfoMapper.queryTagByName(tagName);
            if (tagInfo == null){
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

    private void handlerTag(UpdateDataSchemaRequest schemaRequest){
        List<String> tagNames = schemaRequest.getTagNameList();
        List<DataSchemaTagsEntity> dataSchemaTagsEntityList = dataSchemaTagsMapper.getSchemaTagsMap(schemaRequest.getSchemaId());
        List<Long> tagIds = dataSchemaTagsEntityList.stream()
                .map(DataSchemaTagsEntity::getTagId)
                .distinct()
                .collect(Collectors.toList());
        List<TagInfoEntity> tagInfoEntityList = tagInfoMapper.queryTagByIds(tagIds);
        List<String> tagNameList = tagInfoEntityList.stream()
                .map(TagInfoEntity::getTagName)
                .distinct()
                .collect(Collectors.toList());

        List<String> addTags = tagNames.stream()
                .filter(tagName -> !tagNameList.contains(tagName))
                .collect(Collectors.toList());

        addTags.forEach(tagName -> {
            TagInfoEntity tagInfo = tagInfoMapper.queryTagByName(tagName);
            if (tagInfo == null){
                tagInfo = new TagInfoEntity();
                tagInfo.setTagName(tagName);
                tagInfoMapper.insertItem(tagInfo);
            }
            DataSchemaTagsEntity dataSchemaTagsEntity = new DataSchemaTagsEntity();
            dataSchemaTagsEntity.setDataSchemaId(schemaRequest.getSchemaId());
            dataSchemaTagsEntity.setTagId(tagInfo.getPkId());
            dataSchemaTagsMapper.insertDataSchemaTag(dataSchemaTagsEntity);
        });

        List<String> delTags = tagNameList.stream()
                .filter(str -> !tagNames.contains(str))
                .collect(Collectors.toList());

        List<Long> delTagIds = tagInfoMapper.getTagIdsByNames(delTags);
        dataSchemaTagsMapper.delDataSchemaTag(delTagIds,schemaRequest.getSchemaId());
    }
}
