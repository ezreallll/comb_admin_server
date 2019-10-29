package com.shp.comb.config.shiro;

import com.shp.comb.modle.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Created by shp on 19/10/21.
 */
public class ShiroUtil {

    /**
     * 登录
     * @param account
     * @param password
     */
    public static void login(String account,String password){
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static SysUser getCurrentAccount(){
        Subject currentUser = SecurityUtils.getSubject();
        return (SysUser)currentUser.getPrincipal();
    }

    public static String getToken(){
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getId().toString();
    }

    /**
     * 登出
     */
    public static boolean logout(){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser!=null) {
            currentUser.logout();
            return true;
        }else {
            return false;
        }
    }
}
