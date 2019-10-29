package com.shp.comb.modle.vo.sys;

import com.shp.comb.modle.sys.SysRoleMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shp on 19/10/29.
 */
@Data
public class SysRoleVo implements Serializable {

    private int id;

    private String role_name;

    private String role_desc;

    private List<SysRoleMenu> roles;
}
