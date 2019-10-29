package com.shp.comb.controller;


import com.shp.comb.config.shiro.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shp on 17/4/12.
 */
public abstract class BaseController {


    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);


    public int getUserId(){
        return ShiroUtil.getCurrentAccount().getId();
    }

}
