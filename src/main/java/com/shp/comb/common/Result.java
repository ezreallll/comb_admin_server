package com.shp.comb.common;

import java.util.HashMap;

/**
 * Created by shp on 19/10/21.
 */
public class Result extends HashMap<String, Object>{

    public static Result error(int code, String msg, Object data) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

}
