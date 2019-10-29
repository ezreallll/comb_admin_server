package com.shp.comb.controller.sys.vo;

import lombok.Data;

/**
 * Created by shp on 19/10/28.
 */
@Data
public class SysUserVo {

    private int id;

    private String account;

    private String user_name;

    private String password;

    private int status;

    private int role_id;

    private int page;

    private int size;
}
