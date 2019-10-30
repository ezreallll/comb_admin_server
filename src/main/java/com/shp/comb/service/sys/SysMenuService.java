package com.shp.comb.service.sys;

import com.github.pagehelper.StringUtil;
import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.common.enums.SysMenuTypeEnum;
import com.shp.comb.config.shiro.ShiroUtil;
import com.shp.comb.exception.ServerBizException;
import com.shp.comb.mapper.sys.SysRoleMenuMapper;
import com.shp.comb.modle.sys.SysMenu;
import com.shp.comb.mapper.sys.SysMenuMapper;
import com.shp.comb.modle.sys.SysRoleMenu;
import com.shp.comb.modle.vo.sys.SysMenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by shp on 19/10/21.
 */
@Service
public class SysMenuService {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuService.class);


    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 获取用户菜单
     * @param id
     * @return
     */
    public HashMap getUserInfo(int id){
      HashMap result = new HashMap();
      List<SysMenu> sysMenus = sysMenuMapper.selectMenuByAdmin(id);
      result.put("menuList",buildMenuTree(sysMenuCopyToSysMenuVo(sysMenus),0));
      List<String> perms=new ArrayList<>();
      for(SysMenu sysMenu:sysMenus){
          if(StringUtil.isNotEmpty(sysMenu.getMenu_code())){
              perms.add(sysMenu.getMenu_code());
          }
      }
      result.put("perms",perms);
      result.put("name", ShiroUtil.getCurrentAccount().getUser_name());
      return result;
    }

    /**
     * 获取菜单列表
     * @return
     */
    public List<SysMenuVo> getMenuList(){
        List<SysMenu> sysMenus = sysMenuMapper.selectAllMenu();
        List<SysMenuVo> sysMenuVos = sysMenuCopyToSysMenuVo(sysMenus);
        return  buildMenuTree(sysMenuVos,0);
    }

    /**
     * 添加或者更新
     * @param id
     * @param name
     * @param code
     * @param icon
     * @param type
     * @param url
     * @param order_
     * @param parentId
     * @return
     */
    public boolean addOrUpdateMenu(int id,String name,String code,String icon,int type,String url,int order_,int parentId,String parentName){
        int count = 0;
        SysMenu sysMenu=new SysMenu();
        sysMenu.setMenu_name(name);
        sysMenu.setMenu_code(code);
        sysMenu.setMenu_icon(icon);
        sysMenu.setMenu_type(type);
        sysMenu.setMenu_url(url);
        sysMenu.setOrder_(order_);
        sysMenu.setParent_id(parentId);
        sysMenu.setParent_name(parentName);
        if(id!=0){//编辑
            sysMenu.setId(id);
            count = sysMenuMapper.updateMenu(sysMenu);
        }else { //添加
            sysMenu.setCreate_user(ShiroUtil.getCurrentAccount().getId());
            count = sysMenuMapper.insertMenu(sysMenu);
        }
        return count>0;
    }

    /**
     * 删除菜单
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenu(int id){
        int count = 0;
        sysRoleMenuMapper.deleteByMenuId(id);
        count = sysMenuMapper.deleteMenuById(id);
        return count >0;
    }

    private List<SysMenuVo> sysMenuCopyToSysMenuVo(List<SysMenu> sysMenus){
        List<SysMenuVo> sysMenuVos=new ArrayList<>();
        if(sysMenus!=null&&sysMenus.size()>0){
            for(SysMenu sysMenu:sysMenus){
                SysMenuVo sysMenuVo =new SysMenuVo();
                sysMenuVo.setName(sysMenu.getMenu_name());
                sysMenuVo.setIcon(sysMenu.getMenu_icon());
                sysMenuVo.setId(sysMenu.getId());
                sysMenuVo.setParentId(sysMenu.getParent_id());
                sysMenuVo.setUrl(sysMenu.getMenu_url());
                sysMenuVo.setType(sysMenu.getMenu_type());
                sysMenuVo.setOrder_(sysMenu.getOrder_());
                sysMenuVo.setCode(sysMenu.getMenu_code());
                sysMenuVo.setParentName(sysMenu.getParent_name());
                sysMenuVos.add(sysMenuVo);
            }
            return sysMenuVos;
        }else {
            throw new ServerBizException(ErrorCodeEnum.NO_MENU_PERMISSION);
        }
    }


    public List<SysMenuVo> buildMenuTree(List<SysMenuVo> sysMenuVos,int parentId){
        if(sysMenuVos!=null&&sysMenuVos.size()>0){
            List<SysMenuVo> result=new ArrayList<>();
            for(SysMenuVo sysMenuVo:sysMenuVos){
                if(sysMenuVo.getParentId() == parentId){
                    sysMenuVo.setChildren(buildMenuTree(sysMenuVos,sysMenuVo.getId()));
                    Collections.sort(sysMenuVo.getChildren(), new Comparator<SysMenuVo>() {
                        @Override
                        public int compare(SysMenuVo s1, SysMenuVo s2) {
                            // 根据order升序排列
                            if (s1.getOrder_() > s2.getOrder_()) {
                                return 1;
                            }
                            if (s1.getOrder_() == s2.getOrder_()) {
                                return 0;
                            }
                            return -1;
                        }
                    });
                    result.add(sysMenuVo);
                }
            }
            return result;
        }else {
            throw new ServerBizException(ErrorCodeEnum.NO_MENU_RESULT);
        }
    }


}
