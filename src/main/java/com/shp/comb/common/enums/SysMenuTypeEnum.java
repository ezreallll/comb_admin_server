package com.shp.comb.common.enums;

/**
 * Created by shp on 19/10/25.
 */
public enum SysMenuTypeEnum {

    CATALOG(0,"目录"),
    MENU(1,"菜单"),
    BUTTON(2,"按钮"),
    ;

    private int code;

    private String type;

    SysMenuTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
