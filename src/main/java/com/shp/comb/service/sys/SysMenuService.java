package com.shp.comb.service.sys;

import com.github.pagehelper.StringUtil;
import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.common.enums.SysMenuTypeEnum;
import com.shp.comb.config.shiro.ShiroUtil;
import com.shp.comb.exception.ServerBizException;
import com.shp.comb.modle.sys.SysMenu;
import com.shp.comb.mapper.sys.SysMenuMapper;
import com.shp.comb.modle.vo.sys.SysMenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by shp on 19/10/21.
 */
@Service
public class SysMenuService {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuService.class);


    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 获取用户菜单
     * @param id
     * @return
     */
    public HashMap getUserInfo(int id){
      HashMap result = new HashMap();
      List<SysMenu> sysMenus = sysMenuMapper.selectMenuByAdmin(id);
      result.put("menuList",createMenuTree(sysMenuCopyToSysMenuVo(sysMenus),SysMenuTypeEnum.CATALOG.getCode()));
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
        //获取菜单层级
        List<SysMenuVo> menuList = createMenuTree(sysMenuVos,SysMenuTypeEnum.MENU.getCode());
        List<SysMenuVo> baseSysMenus = new ArrayList<>();
        for(SysMenuVo sysMenuVo:sysMenuVos){
            if(sysMenuVo.getType() == SysMenuTypeEnum.CATALOG.getCode()){
                baseSysMenus.add(sysMenuVo);
            }
        }
        //将目录的集合加进去
        menuList.addAll(baseSysMenus);
        //获取目录层级
        List<SysMenuVo> result=createMenuTree(menuList,SysMenuTypeEnum.CATALOG.getCode());
        return result;
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
    public boolean deleteMenu(int id){
        return sysMenuMapper.deleteMenuById(id)>0;
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


    /**
     * 创建菜单树
     * @param sysMenus
     * @param menuType
     * @return
     */
    private List<SysMenuVo> createMenuTree(List<SysMenuVo> sysMenus,int menuType){
        List<SysMenuVo> menuTree = new ArrayList<>();
        //添加父节点
        for(SysMenuVo sysMenuVo:sysMenus){
            if(sysMenuVo.getType() == menuType){
                menuTree.add(sysMenuVo);
            }
        }
        //添加菜单
        for(SysMenuVo sysMenuVo:menuTree){ //循环父节点
            List<SysMenuVo> childList=new ArrayList<>();
            for(SysMenuVo sysMenuVo1:sysMenus) {
                if (sysMenuVo1.getParentId() == sysMenuVo.getId()) {
                    childList.add(sysMenuVo1);
                }
            }
            //子节点排序
            Collections.sort(childList, new Comparator<SysMenuVo>() {
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
            sysMenuVo.setChildren(childList);
        }
        return menuTree;
    }


}
