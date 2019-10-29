package com.shp.comb.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

	private static Log logger = LogFactory.getLog(RequestUtil.class);

	static{
		//注册一个日期道beansutils,便于map到bean的转换
		/*DateTimeConverter d = new DateTimeConverter();*/
		/*ConvertUtils.register(d, java.util.Date.class);*/
	}

	public static void populate(HttpServletRequest request, Object obj) throws Exception {
		Map map = request.getParameterMap();
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			logger.error("populate request parameters exception,map="+map,e);
			throw e;
		}
	}

	public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
		HashMap<String, String> res = new HashMap<String, String>();
		Enumeration<String> emu = request.getHeaderNames();
		while (emu.hasMoreElements()) {
			String key = emu.nextElement();
			String value = request.getHeader(key);
			res.put(key, value);
		}
		return res;
	}

	public static String getRequestString(HttpServletRequest request, final String name) {
		return getRequestString(request, name, null);
	}

	public static String getRequestString(HttpServletRequest request, final String name, final String defaultValue) {
		String value = request.getParameter(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	public static int getRequestInt(HttpServletRequest request, final String name) {
		return getRequestInt(request, name, 0);
	}



	public static int getRequestInt(HttpServletRequest request, final String name, int defaultValue) {
		String value = getRequestString(request, name, null);
		if (value == null)
			return defaultValue;

		try {
			return NumberUtils.toInt(value.trim(),defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	


	public static long getRequestLong(HttpServletRequest request, final String name) {
		return getRequestLong(request, name, 0);
	}

	public static long getRequestLong(HttpServletRequest request, final String name, long defaultValue) {
		String value = getRequestString(request, name, "");
		return ParseUtil.paseLong(value, defaultValue);
	}

	public static String getRemoteIpAddress(HttpServletRequest request) {
		String rip = request.getRemoteAddr();
		String xff = request.getHeader("X-Forwarded-For");
		String ip;
		if (xff != null && xff.length() != 0) {
			int px = xff.indexOf(',');
			if (px != -1) {
				ip = xff.substring(0, px);
			} else {
				ip = xff;
			}
		} else {
			ip = rip;
		}
		return ip.trim();
	}

	/**
	 * 获取用户地区编码（基于国标）
	 * 
	 * @param request
	 * @return
	 */
	public static String getIploc(HttpServletRequest request) {
		final String DEFAULT_LOCAL = "CN0000";
		String iploc = request.getHeader("cmccip");
		if (iploc == null || iploc.length() == 0 || "unknown".equalsIgnoreCase(iploc)) {
			iploc = DEFAULT_LOCAL;
		}
		return iploc;
	}
	
	public static String getGbgodeFromIploc(HttpServletRequest request){
	    String iploc = getIploc(request);
        if (StringUtils.indexOf(iploc, "CN") != -1) {
            iploc = StringUtils.substring(iploc, 2);
            if (iploc.length() > 6) {
                iploc = StringUtils.substring(iploc, 0, 6);
            }
        }
        if("000000".equalsIgnoreCase(iploc)||"0000".equalsIgnoreCase(iploc)){
//            LogWriter.printlog("[getGbgodeFromIploc]:unknown \t:ip="+getIpAddr(request)+"\t xff"+request.getHeader("X-Forwarded-For")+"\t remoteAddr:"+request.getRemoteAddr());
            return null;
        }
        return iploc;
	}

	/**
	 *  获取客户端ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-source-id");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
	
/*	*//**
	 * 根据Request 获取UserAgentPlatform
	 * 1. 优先通过参数获取userAgentPlatform
	 * 2. 1返回空，使用Header中User-Agent属性来判断platForm
	 * @param request
	 * @return
	 *//*
	public static String getUserAgentPlatform(HttpServletRequest request) {
		String userAgentPlatform = RequestUtil.getRequestString(request, "userAgentPlatform");// 提供UserAgent
		if (StringUtils.isNotBlank(userAgentPlatform) && userAgentPlatform.length() > 1) {
			return userAgentPlatform;
		}
		String userAgent = RequestUtil.getRequestHeaders(request).get("User-Agent");
		//LogWriter.printlog("ZJB: User-Agent=" + userAgent + " isBlank= " + StringUtils.isBlank(userAgent));
		if (StringUtils.isBlank(userAgent)) {
			return "";
		}
		return getUserAgentPlatform(userAgent);
	}*/

/*	*//**
	 * 根据userAgent获取UserAgentPlatform
	 *
	 * @param
	 * @return
	 *//*
	public static String getUserAgentPlatform(String userAgent) {
		UserAgent ua = UserAgent.parseUserAgentString(userAgent);
		String sysName = ua.getOperatingSystem().getName();
		//LogWriter.printlog("ZJB: UserAgent=" + ua + " sysName= " + sysName);
		String platform = "";
		*//*if (StringUtils.containsIgnoreCase(userAgent, "MicroMessenger")) {
			platform = "weixin";
		} else *//*
		if (StringUtils.containsIgnoreCase(sysName, "android")) {
			platform = "Android";
		} else if (StringUtils.containsIgnoreCase(sysName, "iPhone")) {
			platform = "iPhone";
		} else if (StringUtils.containsIgnoreCase(sysName, "iPad")) {
			platform = "iPad";
		} else if (StringUtils.containsIgnoreCase(sysName, "Symbian")) {
			platform = "Symbian";
		} else if (StringUtils.containsIgnoreCase(sysName, "Windows")
				&& (StringUtils.containsIgnoreCase(sysName, "Mobile") || StringUtils.containsIgnoreCase(sysName,
						"Phone"))) {
			platform = "WindowsPhone";
		} else if (StringUtils.containsIgnoreCase(sysName, "Windows")
				|| StringUtils.containsIgnoreCase(sysName, "Linux")) {
			platform = "PC";
		}
		return platform;
	}
	*/


	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(name)) {
					String value = cookies[i].getValue();
					return value;
				}
			}
		}
		return null;
	}
	
}
