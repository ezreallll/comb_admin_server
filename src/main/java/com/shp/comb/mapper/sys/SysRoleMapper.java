package com.shp.comb.mapper.sys;

import com.shp.comb.mapper.BaseMapper;
import com.shp.comb.modle.sys.SysRole;
import com.shp.comb.modle.sys.SysUser;
import com.shp.comb.modle.vo.sys.SysRoleVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shp on 19/10/21.
 */
@Repository
public class SysRoleMapper extends BaseMapper<SysRole,Integer> {

    public List<SysRoleVo> selectRoleListWithMenu(){
        return this.getSqlSession().selectList(getFullSqlName("selectRoleListWithMenu"));
    }
}
