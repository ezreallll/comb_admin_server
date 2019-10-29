package com.shp.comb.mapper.sys;

import com.shp.comb.mapper.BaseMapper;
import com.shp.comb.modle.sys.SysRole;
import com.shp.comb.modle.sys.SysRoleMenu;
import org.springframework.stereotype.Repository;

/**
 * Created by shp on 19/10/21.
 */
@Repository
public class SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Integer> {


    public int deleteByRoleId(int role_id){
        return this.getSqlSession().delete(getFullSqlName("deleteByRoleId"),role_id);
    }
}
