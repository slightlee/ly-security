package com.ly.common.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
    public static final String numberChar = "0123456789" ;

    /**
     * 由大小写字母、数字组成的随机字符串
     *
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 由大小写字母组成的随机字符串
     *
     * @param length
     * @return
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    /**
     * 由小字字母组成的随机字符串
     *
     * @param length
     * @return
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 由大写字母组成的随机字符串
     *
     * @param length
     * @return
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 产生指字个数的0组成的字符串
     *
     * @param length
     * @return
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 将数字转化成指字长度的字符串
     *
     * @param num
     * @param fixdlenth
     * @return
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 将数字转化成指字长度的字符串
     *
     * @param num
     * @param fixdlenth
     * @return
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 这个方法只支持最大长度为32的随机字符串,如要支持更大长度的，可以适当修改此方法，如前面补、后面补，或者多个uuid相连接
     *
     * @param length
     * @return
     */
    public static String toFixedLengthStringByUUID(int length) {

        //也可以通过UUID来随机生成
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-" , "").substring(0, length);
    }


    public static String generate(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }


    public static void main(String[] args) {

        System.out.println(generateString(32));
        System.out.println(generateMixString(32));
        System.out.println(generateLowerString(32));
        System.out.println(generateUpperString(32));
        System.out.println(generateZeroString(32));
        System.out.println(toFixdLengthString(123, 32));
        System.out.println(toFixdLengthString(123L, 32));
        System.out.println(toFixedLengthStringByUUID(32));
        System.out.println(generate(4));
    }
}
