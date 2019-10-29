package com.shp.comb.modle.sys;

import com.shp.comb.modle.DbBaseObject;
import lombok.Data;

/**
 * Created by shp on 19/10/21.
 */
@Data
public class SysRole extends DbBaseObject<Integer> {


    private String role_name;

    private String role_desc;

}
