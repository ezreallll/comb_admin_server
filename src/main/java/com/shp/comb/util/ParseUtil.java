package com.shp.comb.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {

	public static final Log logger = LogFactory.getLog(ParseUtil.class);
	
	public static int paseInt(String dataStr,int defaultV) {
		int r = defaultV;
		try{
            if (NumberUtils.isDigits(dataStr)) {
                r = Integer.parseInt(dataStr);
            }
        } catch (Exception e) {
            logger.error("parse int value error,val:" + dataStr, e);
        }
		return r;
	}

	public static long paseLong(String dataStr,long defaultV) {
		long r = defaultV;
        try {
            if (NumberUtils.isDigits(dataStr)) {
                r = Long.parseLong(dataStr);
            }
        } catch (Exception e) {
            logger.error("parse long value error,val:" + dataStr, e);
        }
		return r;
	}
	
	public static float parseFloat(String dataStr, float defaultV) {
		float r = defaultV;
        try {
            if (NumberUtils.isNumber(dataStr)) {
                r = Float.parseFloat(dataStr);
            }
        } catch (Exception e) {
            logger.error("parse float value error,val:" + dataStr, e);
        }
		return r;
	}
	
	public static int[] parseIntArray(String[] s, int defaultValue) throws Exception {
		if(s == null){
			return null;
		}
		int[] x_ret = new int[s.length];
		for(int i = 0; i < s.length; i ++){
			x_ret[i] = paseInt(s[i], defaultValue);
		}
		return x_ret;
	}
	
	public static List<Integer> parseIntList(String s, String split) throws Exception {
		if (s == null || s.length() == 0) {
			return null;
		}
		if (split == null || split.length() == 0) {
			split = ",";
		}
		String[] arrayStr = s.split(split);
		int[] arrayInt = parseIntArray(arrayStr, 0);
		if (arrayInt == null) {
			return null;
		}

		List<Integer> x_ret = new ArrayList<Integer>();
		for(int i : arrayInt) {
			x_ret.add(i);
		}

		return x_ret;
	}

	public static void main(String[] args) {
		try {
			System.out.println(Long.MAX_VALUE);
			System.out.println(paseLong("5790299393819152393", 0));
			System.out.println(Long.MAX_VALUE > paseLong("5790299393819152393", 0));
			System.out.println(paseLong("5790299393 81915239", 0));
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}
