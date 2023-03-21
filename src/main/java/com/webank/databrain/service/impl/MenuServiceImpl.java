package com.webank.databrain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.bo.RoleMenuBO;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.entity.MenuInfoEntity;
import com.webank.databrain.dao.mapper.MenuInfoMapper;
import com.webank.databrain.service.MenuService;
import com.webank.databrain.vo.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final static int MENU_PARENT_ID = 0; // parentId = 0 为一级目录

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    @Override
    public CommonResponse getMenuByRole() {

        // 1. 获取登录态下用户的角色
        LoginUserBO bo = (LoginUserBO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int roleType = bo.getEntity().getAccountType();

        // 2. 根据用户角色组装角色菜单
        List<RoleMenuBO> menuList = getMenus(roleType);
        return CommonResponse.success(menuList);
    }

    private List<RoleMenuBO> getMenus(int roleType){

        // 1. 获取所有菜单
        List<MenuInfoEntity> entityList = menuInfoMapper.getAllMenu();

        // 2. 获取所有一级菜单
        List<RoleMenuBO> menuList = entityList.stream()
                .filter(item -> item.getMenuRole() == roleType && item.getParentId() == MENU_PARENT_ID)
                .map(item -> new RoleMenuBO(item.getMenuId(), item.getMenuName(), item.getParentId(), item.getMenuUrl()))
                .collect(Collectors.toList());

        // 3. 获取所有一级菜单的子菜单
        menuList.stream().forEach(menuItem -> {
            menuItem.setChildren(getChildren(menuItem.getMenuId(), roleType, entityList));
        });

        // 4. 返回菜单结果
        return menuList;
    }

    private List<RoleMenuBO> getChildren(int parentId, int roleType, List<MenuInfoEntity> allMenuList){
        List<RoleMenuBO> children = allMenuList.stream()
                .filter(item -> item.getMenuRole() == roleType && item.getParentId() == parentId)
                .map(item -> new RoleMenuBO(item.getMenuId(), item.getMenuName(), item.getParentId(), item.getMenuUrl()))
                .collect(Collectors.toList());

        children.stream().forEach(child -> child.setChildren(getChildren(child.getMenuId(), roleType, allMenuList)));

        if(CollectionUtil.isEmpty(children)){
            return null;
        }

        return children;
    }
}
