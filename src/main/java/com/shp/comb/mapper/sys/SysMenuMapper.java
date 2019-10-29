package com.shp.comb.mapper.sys;

import com.shp.comb.modle.sys.SysMenu;
import com.shp.comb.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shp on 19/10/23.
 */
@Repository
public class SysMenuMapper extends BaseMapper<SysMenu,Integer> {

    public  List<SysMenu> selectMenuByAdmin(int u_id){
        return this.getSqlSession().selectList(getFullSqlName("selectMenuByAdmin"),u_id);
    }

    public List<SysMenu> selectAllMenu(){
        return this.getSqlSession().selectList(getFullSqlName("selectAllMenu"));
    }

    public int insertMenu(SysMenu sysMenu){
        return this.getSqlSession().insert(getFullSqlName("insertMenu"),sysMenu);
    }

    public int updateMenu(SysMenu sysMenu){
        return this.getSqlSession().update(getFullSqlName("updateMenu"),sysMenu);
    }

    public int deleteMenuById(int id){
        return this.getSqlSession().delete(getFullSqlName("deleteMenuById"),id);
    }
}
