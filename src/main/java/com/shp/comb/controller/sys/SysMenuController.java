package com.shp.comb.controller.sys;

import com.shp.comb.controller.sys.vo.SysMenuVoD;
import com.shp.comb.service.sys.SysMenuService;
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
@RequestMapping("/sys/menu")
public class SysMenuController {


    @Autowired
    private SysMenuService sysMenuService;


    @RequiresPermissions("sys:menu")
    @RequestMapping("/list")
    public void getMenuList(HttpServletResponse response){
        ResponseUtil.renderSuccessJson(response,"OK",sysMenuService.getMenuList());
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("/addOrUpdate")
    public void addOrUpdate(HttpServletResponse response, @RequestBody SysMenuVoD sysMenuVoD){
        if(sysMenuService.addOrUpdateMenu(
                sysMenuVoD.getId(),
                sysMenuVoD.getName(),
                sysMenuVoD.getPerms(),
                sysMenuVoD.getIcon(),
                sysMenuVoD.getType(),
                sysMenuVoD.getUrl(),
                sysMenuVoD.getOrderNum(),
                sysMenuVoD.getParentId(),
                sysMenuVoD.getParentName()
        )){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }

    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/del")
    public void deleteMenu(HttpServletResponse response, @RequestBody SysMenuVoD sysMenuVoD){
        if(sysMenuService.deleteMenu(sysMenuVoD.getId())){
            ResponseUtil.renderSuccessTipJson(response,"OK");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }





}
