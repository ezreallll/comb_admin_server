package com.shp.comb.modle.sys;

import com.shp.comb.modle.DbBaseObject;
import lombok.Data;

/**
 * Created by shp on 19/10/21.
 */
@Data
public class SysMenu extends DbBaseObject<Integer> {

    private String menu_name;

    private String menu_code;

    private String menu_icon;

    private int menu_type;

    private String menu_url;

    private int order_;

    private int parent_id;

    private String parent_name;

    private int create_user;

}
