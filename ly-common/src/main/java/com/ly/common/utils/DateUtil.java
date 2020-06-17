package com.ly.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
//
public class DateUtil   {
    /**
     * @yww 测试方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        LocalDate idate=LocalDate.now();
        String s="2017/07/20";
        //System.out.println("时间：：："+ string_to_date(s));
        System.out.println(minusSeconds(5) );
    }

    /**
     * date转字符串
     * @param date 当前时间
     * @param parm 格式随意定义"yyyy-mm-dd hh:mm:dd" 或者"yyyy-mm-dd"
     * @return 返回字符串
     */
    public static String date_to_String(LocalDateTime date,String parm) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(parm);
        return  formatter.format(localDateTime);
    }
    /**
     * @param date  时间 "2017/07/20 12:13:12"
     * @return      结果：2017-07-20T12:13:12
     */
    public static LocalDateTime string_to_date(String  date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        return localDateTime;
     }
    /**
     * @param date  时间 "2017/07/20"
     * @return      结果：2017-07-20
     */
    public static LocalDate ldate(String  date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDateTime = LocalDate.parse(date, dateTimeFormatter);
        return localDateTime;
    }

    /**
     * 当前时间加几秒后的时间
     * @param second
     * @return
     */
    public static LocalDateTime  plusSeconds(int second){
        LocalDateTime localDateTime=LocalDateTime.now();
        return localDateTime.plusSeconds(second);
    }
    /**
     * 当前时间减几秒后的时间
     * @param second
     * @return
     */
    public static LocalDateTime  minusSeconds(int second){
        LocalDateTime localDateTime=LocalDateTime.now();
        return localDateTime.minusSeconds(second);
    }

    /**
     * 当前时间加几分钟后的时间
     * @param second
     * @return
     */
    public static LocalDateTime  plusMinutes(int second){
        LocalDateTime localDateTime=LocalDateTime.now();
        return localDateTime.plusMinutes(second);
    }





}