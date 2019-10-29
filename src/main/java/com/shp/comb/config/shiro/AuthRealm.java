package com.shp.comb.config.shiro;



import com.github.pagehelper.StringUtil;
import com.shp.comb.mapper.sys.SysMenuMapper;
import com.shp.comb.modle.sys.SysMenu;
import com.shp.comb.modle.sys.SysUser;
import com.shp.comb.exception.ServerBizException;
import com.shp.comb.service.sys.SysMenuService;
import com.shp.comb.service.sys.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by shp on 18/3/29.
 */
@Repository
public class AuthRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuService.class);


    @Autowired
    private SysUserService userService;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 鉴权
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SysUser sysUser= (SysUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        List<SysMenu> sysMenus=sysMenuMapper.selectMenuByAdmin(sysUser.getId());
        Set<String> role=new HashSet<String>();
        for (SysMenu sysMenu:sysMenus){
            logger.info(sysMenu.getMenu_code()+"fdsafsdsaf");
            if(StringUtil.isNotEmpty(sysMenu.getMenu_code())) {
                role.add(sysMenu.getMenu_code());
            }
        }
        authorizationInfo.setStringPermissions(role);
        return authorizationInfo;
    }


    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();  //得到用户名
        String password = new String((char[]) token.getCredentials()); //得到密码

        SysUser sysUserPara = new SysUser();
        sysUserPara.setAccount(account);
        sysUserPara.setPassword(password);
        try {
            sysUserPara =  userService.login(sysUserPara);
        } catch (ServerBizException e) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(sysUserPara,password,getName());
    }
}