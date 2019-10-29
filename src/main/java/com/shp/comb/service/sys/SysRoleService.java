package com.shp.comb.service.sys;

import com.shp.comb.controller.sys.vo.SysRoleMenuVo;
import com.shp.comb.mapper.sys.SysRoleMapper;
import com.shp.comb.mapper.sys.SysRoleMenuMapper;
import com.shp.comb.modle.sys.SysRole;
import com.shp.comb.modle.sys.SysRoleMenu;
import com.shp.comb.modle.vo.sys.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shp on 19/10/28.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    public List<SysRole> getRoleList(){
        return  sysRoleMapper.selectList(null);
    }

    public List<SysRoleVo> getRoleListWithMenu(){
        return sysRoleMapper.selectRoleListWithMenu();
    }

    public boolean addOrUpdateRole(int id,String name,String desc){
        int count = 0;
        SysRole sysRole=new SysRole();
        sysRole.setRole_name(name);
        sysRole.setRole_desc(desc);
        if(id != 0){
            sysRole.setId(id);
            count = sysRoleMapper.updateById(sysRole);
        }else {
            count = sysRoleMapper.insert(sysRole);
        }
        return count>0;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean delRole(int id){
        int count = 0;
        sysRoleMenuMapper.deleteByRoleId(id);
        count = sysRoleMapper.deleteById(id);
        return count>0;
    }

    /**
     * 保存角色的权限
     * @param sysRoleMenuVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenuPerms(SysRoleMenuVo sysRoleMenuVo){
        int role_id =  sysRoleMenuVo.getRole_id();
        //删除该角色的所有权限
        sysRoleMenuMapper.deleteByRoleId(role_id);
        List<SysRoleMenu> sysRoleMenus=new ArrayList<>();
        for(int menuId:sysRoleMenuVo.getMenus()){
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setMenu_id(menuId);
            sysRoleMenu.setRole_id(role_id);
            sysRoleMenus.add(sysRoleMenu);
        }
        //添加
        sysRoleMenuMapper.insertList(sysRoleMenus);
    }
}
