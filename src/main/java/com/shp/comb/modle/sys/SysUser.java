package com.shp.comb.modle.sys;

import com.shp.comb.modle.DbBaseObject;
import lombok.Data;

/**
 * Created by shp on 19/10/21.
 */
@Data
public class SysUser extends DbBaseObject<Integer> {

    private String account;

    private String user_name;

    private String password;

    private int status;

    private int role_id;

    private int create_user;

}
