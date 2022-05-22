package com.tdh.common;


import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String currenttime = DateUtils.formatter.format(new Date());

    public static boolean authorize_date(String date) {
        if (1 == compare_date(currenttime, date) || 0 == compare_date(currenttime, date)) {
            return true;
        }
        return false;
    }

    /**
     * 获取如期星期几
     * @param date
     * @return
     */
    public static Integer dateToWeek(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date datet = null;
        try {
            datet = simpleDateFormat.parse(date);
            calendar.setTime(datet);
        } catch (Exception e) {
            // TODO: handle exception
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(w<0){
            return 0;
        }else{
            return w;
        }
    }
    public static int compare_date(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public static String getYYYYMMDDHHMISS() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = fmt.format(new Date());
        return time;
    }

    public static String getYYYYMMDD() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String time = fmt.format(new Date());
        return time;
    }

    public static String getAge(String birth){
        String now = DateUtils.getYYYYMMDD().substring(0,4);
        String after = birth.substring(0,4);
        int a = Integer.parseInt(now);
        int b = Integer.parseInt(after);
        int c = a-b;
        return String.valueOf(c);
    }

    /**
     * s时间格式转换
     * @param format
     * @param date
     * @return
     */
    public static String convertDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间格式转换
     * @param format
     * @param dateStr
     * @return
     */
    public static Date convertString2Date(String format, String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算间隔时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static int calculateTimeOfMin(String startTime, String endTime,int num) {
        Date startDate = DateUtils.convertString2Date("HH:mm", startTime);
        Date endDate = DateUtils.convertString2Date("HH:mm", endTime);
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000) / 60/ num;
        return c;
    }

    /**
     * 获取星期
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 时间格式转换
     * @param date
     * @return
     */
    public static Date handleStringToDate(String date) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date s = sdf.parse(date);
        return s;
    }


    public static int handleDateSubtraction(long t1, long t2) throws Exception{
        long diff=(t2-t1)/1000/60;
        return Integer.parseInt(String.valueOf(diff));
    }

    public static long handleStrDataToLongData(String data)throws Exception{
        long result =0;
        try {
            if(StringUtils.isEmpty(data)){
                data = DateUtils.getYYYYMMDDHHMISS();
            }
            result = Long.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFetureDate(int past) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);

        Date today = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String result = format.format(today);

        return result;

    }



}