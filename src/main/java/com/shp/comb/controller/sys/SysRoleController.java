package com.shp.comb.controller.sys;

import com.shp.comb.controller.sys.vo.SysRoleMenuVo;
import com.shp.comb.controller.sys.vo.SysRoleVoD;
import com.shp.comb.service.sys.SysRoleService;
import com.shp.comb.util.ResponseUtil;
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

    @RequestMapping("/list")
    public void getRoleList(HttpServletResponse response){
        ResponseUtil.renderSuccessJson(response,"ok",sysRoleService.getRoleList());
    }

    @RequestMapping("/listWithMenu")
    public void getRoleMenuList(HttpServletResponse response){
        ResponseUtil.renderSuccessJson(response,"ok",sysRoleService.getRoleListWithMenu());
    }


    @RequestMapping("/addOrUpdate")
    public void addOrUpdateRole(HttpServletResponse response,@RequestBody SysRoleVoD sysRoleVoD){
        if(sysRoleService.addOrUpdateRole(sysRoleVoD.getId(), sysRoleVoD.getRole_name(), sysRoleVoD.getRole_desc())){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }

    @RequestMapping("/del")
    public void delRole(HttpServletResponse response,@RequestBody SysRoleVoD sysRoleVoD){
        if(sysRoleService.delRole(sysRoleVoD.getId())){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }

    @RequestMapping("/saveRoleMenuPerms")
    public void saveRoleMenuPerms(HttpServletResponse response, @RequestBody SysRoleMenuVo sysRoleMenuVo){
        sysRoleService.saveRoleMenuPerms(sysRoleMenuVo);
        ResponseUtil.renderSuccessTipJson(response,"OK");
    }




}
