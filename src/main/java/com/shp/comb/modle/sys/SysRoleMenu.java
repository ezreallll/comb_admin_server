package com.shp.comb.modle.sys;

import com.shp.comb.modle.DbBaseObject;
import lombok.Data;

/**
 * Created by shp on 19/10/21.
 */
@Data
public class SysRoleMenu extends DbBaseObject<Integer> {


    private int role_id;

    private int menu_id;


}
