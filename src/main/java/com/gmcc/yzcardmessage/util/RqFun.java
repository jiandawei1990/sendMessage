package com.gmcc.yzcardmessage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RqFun {

    public static void main(String[] args) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1= null;
        Date d2= null;
//        String name="chart";
//        System.out.println(name.charAt(0));
        try {
            d1 = sdf.parse("2021-04-24 10:10:10");
            d2 = sdf.parse("2021-08-02 10:10:10");
            System.out.println(daysBetween(d1,d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        long time3 = cal.getTimeInMillis();
        long between_daysq = (time2-time1)/(1000*3600*24);
       // System.out.println(between_sq);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static int daysBetweenData(Date smdate,Date bddate) throws ParseException{
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bddate = sdf.parse(sdf.format(bddate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bddate);
        long time2 = cal.getTimeInMillis();
        long between_day = (time2-time1)/(1000*3600*24);
        return  Integer.parseInt(String.valueOf(between_day));
    }


    public static int daysBetweenData1(Date smdate,Date bddate) throws ParseException{
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bddate = sdf.parse(sdf.format(bddate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bddate);
        long time2 = cal.getTimeInMillis();
        long between_day = (time2-time1)/(1000*3600*24);
        return  Integer.parseInt(String.valueOf(between_day));
    }








}
