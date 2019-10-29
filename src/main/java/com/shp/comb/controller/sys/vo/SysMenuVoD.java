package com.shp.comb.controller.sys.vo;

import lombok.Data;

/**
 * Created by shp on 19/10/25.
 */
@Data
public class SysMenuVoD {

    private int id;
    private int type;
    private String name;
    private int parentId;
    private String parentName;
    private String url;
    private String perms;
    private int orderNum;
    private String icon;
}
