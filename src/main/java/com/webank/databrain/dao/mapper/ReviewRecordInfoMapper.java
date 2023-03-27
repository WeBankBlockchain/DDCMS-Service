package com.webank.databrain.dao.mapper;

import com.webank.databrain.dao.entity.ReviewRecordInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReviewRecordInfoMapper {


    @Insert("INSERT INTO t_review_record_info(" +
            "item_id," +
            "item_type," +
            "review_state," +
            "agree_count," +
            "deny_count," +
            "witness_count" +
            ") " +
            "VALUES(" +
            "#{itemId}, " +
            "#{itemType}," +
            "#{reviewState}," +
            "#{agreeCount}," +
            "#{denyCount}," +
            "#{witnessCount}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
    void insertReviewRecordInfo(ReviewRecordInfoEntity reviewRecordInfoEntity);


    @Update("UPDATE t_review_record_info SET " +
            "review_state=#{reviewState}, " +
            "agree_count=#{agreeCount}, " +
            "deny_count=#{denyCount} " +
            "WHERE item_id=#{itemId} and item_type=#{itemType}")
    void updateDataSchemaInfo(ReviewRecordInfoEntity reviewRecordInfoEntity);
}
