package com.shp.comb.controller.sys;

import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.config.shiro.ShiroUtil;
import com.shp.comb.controller.BaseController;
import com.shp.comb.controller.sys.vo.LoginVo;
import com.shp.comb.service.sys.SysMenuService;
import com.shp.comb.service.sys.SysUserService;
import com.shp.comb.util.RequestUtil;
import com.shp.comb.util.ResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shp on 19/10/21.
 */
@Controller
@RequestMapping("/sys")
public class SysLoginController extends BaseController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/login")
    public void login(HttpServletResponse response, @RequestBody LoginVo loginVo){

        try {
            String password=userService.serverMD5Password(loginVo.getPassword());
            ShiroUtil.login(loginVo.getAccount(),password);
            ResponseUtil.renderSuccessJson(response,"登录成功",ShiroUtil.getToken());
        }catch (UnknownAccountException e){
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.LOGIN_ACCOUNT_PASSWORD_ERROR);
        }catch (AuthenticationException e){
            logger.error(" exception", e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.LOGIN_ERROR);
        } catch (Exception e) {
             ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
             logger.error(" exception" , e);
        }

    }

    /**
     * 登出
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) {
        try {
            if(ShiroUtil.logout()){
                ResponseUtil.renderSuccessTipJson(response, "退出登录");
            }else {
                ResponseUtil.renderFailJson(response,"退出失败，请重新登录");
            }
        }catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }

    /**
     * 登陆以后获取用户菜单栏
     */
    @RequestMapping("/userInfo")
    public void getNavByUser(HttpServletResponse response){
        try {
           ResponseUtil.renderSuccessJson(response,"登录成功",sysMenuService.getUserInfo(getUserId()));
        } catch (Exception e) {
            logger.error(" exception" , e);
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.SERVER_SYSERR);
        }
    }
}
