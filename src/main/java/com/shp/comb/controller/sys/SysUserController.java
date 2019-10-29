package com.shp.comb.controller.sys;

import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.controller.BaseController;
import com.shp.comb.controller.sys.vo.SysUserVo;
import com.shp.comb.service.sys.SysUserService;
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
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/addOrUpdate")
    public void addUser(HttpServletResponse response,@RequestBody SysUserVo sysUserVo){
        try {

            if(sysUserService.addOrUpdateSysUser(sysUserVo.getId(),sysUserVo.getAccount(),sysUserVo.getUser_name(),sysUserVo.getPassword(),
                    sysUserVo.getStatus(),sysUserVo.getRole_id()
            )){
                ResponseUtil.renderSuccessTipJson(response,"ok");
            }else {
                ResponseUtil.renderFailJson(response,"操作失败");
            }
        } catch (Exception e) {
             ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }


    @RequestMapping("/list")
    public void getUserList(HttpServletResponse response,@RequestBody SysUserVo sysUserVo){
        ResponseUtil.renderSuccessJson(response,"ok",sysUserService.selectUserList(sysUserVo.getPage(),sysUserVo.getSize()));
    }

    @RequestMapping("/del")
    public void deleteUser(HttpServletResponse response,@RequestBody SysUserVo sysUserVo){
        if(sysUserService.deleteUser(sysUserVo.getId())){
            ResponseUtil.renderSuccessTipJson(response,"ok");
        }else {
            ResponseUtil.renderFailJson(response,"操作失败");
        }
    }
}
