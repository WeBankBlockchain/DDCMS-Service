package com.webank.databrain.bo;

import lombok.Data;
import java.util.List;

@Data
public class RoleMenuBO {
    private int menuId;
    private String menuName;
    private int parentId;
    private String menuUrl;
    private List<RoleMenuBO> children;

    public RoleMenuBO(int menuId, String menuName, int parentId, String menuUrl){
        this.menuId = menuId;
        this.menuName = menuName;
        this.parentId = parentId;
        this.menuUrl = menuUrl;
    }
}
