package com.shp.comb.mapper.sys;

import com.shp.comb.modle.sys.SysUser;
import com.shp.comb.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shp on 19/10/21.
 */
@Repository
public class SysUserMapper extends BaseMapper<SysUser,Integer> {

    public SysUser login(SysUser sysUser){
        return this.getSqlSession().selectOne(getFullSqlName("login"),sysUser);
    }

    public int insertSysUser(SysUser sysUser){
        return this.getSqlSession().insert(getFullSqlName("insertSysUser"),sysUser);
    }

    public List<SysUser> selectSysUser(){
        return  this.getSqlSession().selectList(getFullSqlName("selectSysUser"));
    }

    public int updateByUserId(SysUser sysUser){
        return this.getSqlSession().update(getFullSqlName("updateByUserId"),sysUser);
    }
}
