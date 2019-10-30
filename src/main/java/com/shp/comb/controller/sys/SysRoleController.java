package com.shp.comb.controller.sys;

import com.shp.comb.controller.sys.vo.SysRoleMenuVo;
import com.shp.comb.controller.sys.vo.SysRoleVoD;
import com.shp.comb.service.sys.SysRoleService;
import com.shp.comb.util.ResponseUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shp on 19/10/21.
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequiresPermissions("sys:user:edit")
    @RequestMapping("/list")
    public void getRoleList(HttpServletResponse response){
        ResponseUtil.renderSuccessJson(response,"ok",sysRoleService.getRoleList());
    }

    @RequiresPermissions("sys:role")
    @RequestMapping("/listWithMenu")
    public void getRoleMenuList(HttpServletResponse response){
        ResponseUtil.renderSuccessJson(response,"ok",sysRoleService.getRoleListWithMenu());
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/addOrUpdate")
    public void addOrUpdateRole(HttpServletResponse response,@RequestBody SysRoleVoD sysRoleVoD){
        if(sysRoleService.addOrUpdateRole(sysRoleVoD.getId(), sysRoleVoD.getRole_name(), sysRoleVoD.getRole_desc())){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }
    @RequiresPermissions("sys:role:delete")
    @RequestMapping("/del")
    public void delRole(HttpServletResponse response,@RequestBody SysRoleVoD sysRoleVoD){
        if(sysRoleService.delRole(sysRoleVoD.getId())){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/saveRoleMenuPerms")
    public void saveRoleMenuPerms(HttpServletResponse response, @RequestBody SysRoleMenuVo sysRoleMenuVo){
        sysRoleService.saveRoleMenuPerms(sysRoleMenuVo);
        ResponseUtil.renderSuccessTipJson(response,"OK");
    }




}
