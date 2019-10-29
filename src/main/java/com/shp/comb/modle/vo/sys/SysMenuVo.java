package com.shp.comb.modle.vo.sys;

import lombok.Data;

import java.util.List;

/**
 * Created by shp on 19/10/24.
 */
@Data
public class SysMenuVo {


    private int id;

    private String name;

    private String code;

    private String icon;

    private int type;

    private String url;

    private int order_;

    private int parentId;

    private String parentName;

    List<SysMenuVo> children;
}
