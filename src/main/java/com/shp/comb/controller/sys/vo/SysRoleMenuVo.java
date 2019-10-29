package com.shp.comb.controller.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by shp on 19/10/29.
 */
@Data
public class SysRoleMenuVo {
    private int role_id;

    private List<Integer> menus;
}
