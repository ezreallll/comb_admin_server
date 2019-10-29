package com.shp.comb.util;



import com.shp.comb.common.ErrorCodeEnum;
import com.shp.comb.exception.ServerBizException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyDateUtil extends DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(MyDateUtil.class);

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_NO_SECOND= "yyyy-MM-dd HH:mm";
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CHINESE_DATETIME_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
    public static final String CHINESE_DATETIME_HOUR_FORMAT = "yyyy年MM月dd日HH点mm分";
    public static final String STANDARD_CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String ORDER_DATE = "yyyyMMdd";

    /**
     * quartz里面的时间格式
     */
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";



    /**
     * 得到当前日期
     *
     *
     * @return
     */
    public static final String getCurrentDateStr(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String cdate = sdf.format(cal.getTime());
        return cdate;
    }

    /**
     * 获取下一年的今天的日期
     * @param format
     * @return
     */
    public static final String getNextYearDateStr(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String cdate = sdf.format(cal.getTime());
        return cdate;
    }


    public static String date2dateStr(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /**
     * 时间字符串转化成时间戳
     * @param date_str
     * @return
     */
    public static String dateStr2TimeStamp(String date_str){
                 try {
                         SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
                         return String.valueOf(sdf.parse(date_str.trim()).getTime());
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
          return "";
     }

    /**
     * 时间戳为转化为时间字符串
     * @param timeStamp
     * @return
     * @throws Exception
     */
    public static final String timeStamp2DateStr(Long timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        String date = null;
        date = sdf.format(timeStamp);
        return date;
    }

    public static final Date timeStamp2Date(Long timeStamp){
        return  dateStr2Date(timeStamp2DateStr(timeStamp),STANDARD_DATE_FORMAT);
    }


    /**
     * 时间字符串转化为Date
     * @param dateStr
     * @param format
     * @return
     */
    public static final Date dateStr2Date(String dateStr,String format)  {
        if (StringUtils.isBlank(dateStr)){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            logger.error("date parse exception,dateStr="+dateStr,e);
        }
        return null;
    }


    /**
     * 返回短日期，yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static final Date getShortDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回当天的中文星期几
     *
     * @return
     */
    public static String currentDayInWeek() {
        return dayInWeek(new Date());
    }

    /**
     * 返回中文星期几
     *
     * @param d 传入的日期
     * @return
     */
    public static String dayInWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int time = c.get(Calendar.DAY_OF_WEEK);
        String[] weekNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String weekName = weekNames[time - 1];
        return weekName;
    }

    /**
     * 获得两个日期之间相差的天数(返回值去掉了小数部分，即只返回)。（date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int intervalDays(Date date1, Date date2) {
        long intervalMillSecond = getShortDate(date1).getTime() - getShortDate(date2).getTime();
        return (int) (intervalMillSecond / (24 * 3600 * 1000));
    }

    /*
    * 取本周7天的第一天（周一的日期）
    */
    public static Date getFirstDayOfThisWeek() {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0) {
            mondayPlus = -6;
        }else {
            mondayPlus = 1 - dayOfWeek;
        }
        return DateUtils.addDays(getShortDate(new Date()),mondayPlus);
    }

    /*
    * 取下周第一天（周一的日期）
    */
    public static Date getFirstDayOfNextWeek() {
        int mondayPlus;
        Calendar cal = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0) {
            mondayPlus = 1;
        }else {
            mondayPlus = 8 - dayOfWeek;
        }
        return DateUtils.addDays(getShortDate(new Date()),mondayPlus);
    }




    /***
     *通过日期获取定时器里面的时间格式
     * @param date 时间
     * @return  cron类型的日期
     */
    public static String getCron(final Date  date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }


    /***
     *通过定时器里面的时间格式获取日期
     * @param cron Quartz cron的类型的日期
     * @return  Date日期
     */

    public static Date getDate(final String cron) throws Exception{
        if(cron == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            throw new ServerBizException(ErrorCodeEnum.TIME_FORMAT_ERROR);
        }
        return date;
    }

    /**
     * 获取上个月一号零点
     * @return
     */
    public static String getLastMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal.set(Calendar.MINUTE, 0);
        //将秒至0
        cal.set(Calendar.SECOND,0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return timeStamp2DateStr(cal.getTimeInMillis());
    }

    /**
     * 获取上个月最后一天
     * @return
     */
    public static String getLastMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至11
        cal.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        cal.set(Calendar.MINUTE, 59);
        //将秒至59
        cal.set(Calendar.SECOND,59);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return timeStamp2DateStr(cal.getTimeInMillis());
    }


    /**
     * 获取上一年的第一天
     * @param
     * @throws ServerBizException
     */
    public static String getLastYearFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal.set(Calendar.MINUTE, 0);
        //将秒至0
        cal.set(Calendar.SECOND,0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return timeStamp2DateStr(cal.getTimeInMillis());
    }

    /**
     * 获取上一年的最后一天
     * @param
     * @throws ServerBizException
     */

    public static String getLastYearLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH,11);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));;//设置为1号,当前日期既为本月第一天
        //将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        cal.set(Calendar.MINUTE, 59);
        //将秒至0
        cal.set(Calendar.SECOND,59);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return timeStamp2DateStr(cal.getTimeInMillis());
    }

    /**
     * 判断日期是否为昨天
     * @return 1 ：同一天.    0：昨天 .   -1 ：至少是前天.
     * @throws ParseException
     */
    public static int isYeaterday(Date lastTime) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(new Date());
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if((today.getTime()-lastTime.getTime())>0 && (today.getTime()-lastTime.getTime())<=86400000) {
            return 0;
        }else if((today.getTime()-lastTime.getTime())<=0){ //至少是今天
            return 1;
        }else{ //至少是前天
            return -1;
        }
    }


    public static void main(String[] args) throws ServerBizException {
        /*System.out.println(getFirstDayOfThisWeek());
        System.out.println(getFirstDayOfNextWeek());

        System.out.println(getDateStr(new Date(),CHINESE_DATETIME_HOUR_FORMAT));*/
//        logger.info( dateStr2Date("2017-03-10 10:33","yyyy-MM-dd HH:mm")+"----"+System.currentTimeMillis());
        logger.info(getLastYearFirstDay()+":"+getLastYearLastDay());
        logger.info(getLastMonthFirstDay()+":"+getLastMonthLastDay());
    }


}
