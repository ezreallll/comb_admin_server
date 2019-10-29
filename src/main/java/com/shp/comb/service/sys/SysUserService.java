package com.shp.comb.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.config.shiro.ShiroUtil;
import com.shp.comb.modle.PageData;
import com.shp.comb.modle.sys.SysUser;
import com.shp.comb.exception.ServerBizException;
import com.shp.comb.mapper.sys.SysUserMapper;
import com.shp.comb.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shp on 19/10/21.
 */
@Service
public class SysUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserService.class);


    private static final String salt = "LJjDGzc1Yfh97ziF";

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 登录验证
     * @param sysUserPara
     * @return
     */
    public SysUser login(SysUser sysUserPara){
        SysUser sysUser=sysUserMapper.login(sysUserPara);
        if(sysUser!=null){
            return sysUser;
        }else {
            throw new ServerBizException(ErrorCodeEnum.LOGIN_ERROR);
        }
    }

    /**
     * 添加系统用户
     * @return
     */
    public boolean addOrUpdateSysUser(int id,String account,String user_name,String password,int status,int role_id){
        int count = 0;
        SysUser sysUser=new SysUser();
        sysUser.setAccount(account);
        sysUser.setUser_name(user_name);
        if(StringUtil.isNotEmpty(password)) {
            sysUser.setPassword(serverMD5Password(password));
        }
        sysUser.setStatus(status);
        sysUser.setRole_id(role_id);
        if(id!=0){//编辑
            sysUser.setId(id);
            count = sysUserMapper.updateByUserId(sysUser);
        }else { //添加
            sysUser.setCreate_user(ShiroUtil.getCurrentAccount().getId());
            count = sysUserMapper.insertSysUser(sysUser);
        }
        return count>0;
    }

    public PageData<SysUser> selectUserList(int page,int size){
        PageData<SysUser> pageData=new PageData<>();
        PageHelper.startPage(page,size);
        List<SysUser> sysUsers = sysUserMapper.selectSysUser();
        pageData.convertPageData(sysUsers);
        return pageData;
    }


    public  boolean deleteUser(int userId){
        int count = 0;
        count = sysUserMapper.deleteById(userId);
        return  count>0 ;
    }

    public static String serverMD5Password(String password) {
        return MD5Util.MD5Encode(password + salt);
    }

}
