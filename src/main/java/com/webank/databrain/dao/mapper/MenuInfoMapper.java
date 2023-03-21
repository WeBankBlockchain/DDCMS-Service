package com.webank.databrain.dao.mapper;

import com.webank.databrain.dao.entity.MenuInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuInfoMapper {

    @Select("SELECT * FROM t_menu_info ORDER BY parent_id, pk_id")
    List<MenuInfoEntity> getAllMenu();
}
