package com.knight.jone.mySuperDemo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据处理工具类
 */
public class DataDisposeUtil {

    public static int getBit(int data, int bit) {
        return (((data) >> (bit)) & 1);
    }

    /**
     * Description: 负数byte转正int <BR>
     */
    public static Integer byteToInteger(Byte b) {
        return 0xff & b;
    }

    public static int[] byteArray2IntArray(byte[] bytes) {
        int[] ints = new int[bytes.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = 0xff & bytes[i];
        }
        return ints;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param isDivision 是否添加空格分割
     * @return String 每个Byte值
     */
    public static String byte2HexStr(byte[] b, boolean isDivision) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            if (isDivision) {
                sb.append(" ");
            }
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 将int数字转为16进制字符串
     *
     * @param num
     * @return
     */
    public static String int2HexStr(int num) {
        String s = Integer.toHexString(num & 0xFF);
        return (s.length() == 1) ? "0" + s : s;
    }

    /**
     * 16进制字符串转为字节数组
     *
     * @param hexString 16进制字符串
     * @return String （字符集：UTF-8）
     * @explain
     */
    public static byte[] hexString2ByteArray(String hexString) {
        if (hexString == null || "".equals(hexString.trim())) {
            return new byte[0];
        }

        byte[] bytes = new byte[hexString.length() / 2];
        String hex;
        for (int i = 0; i < hexString.length() / 2; i++) {
            hex = hexString.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(hex, 16);
        }

        return bytes;
    }

    /**
     * 16进制字符串转为字节数组
     *
     * @param hexString 16进制字符串
     * @return String （字符集：UTF-8）
     * @explain
     */
    public static int[] hexString2IntArray(String hexString) {
        if (hexString == null || "".equals(hexString.trim())) {
            return new int[0];
        }

        int[] ints = new int[hexString.length() / 2];
        String hex;
        for (int i = 0; i < hexString.length() / 2; i++) {
            hex = hexString.substring(i * 2, i * 2 + 2);
            ints[i] = Integer.parseInt(hex, 16);
        }

        return ints;
    }

    /**
     * 16进制直接转换成为字符串
     *
     * @param hexString 16进制字符串
     * @return String （字符集：UTF-8）
     * @explain
     */
    public static String fromHexString(String hexString) throws Exception {
        // 用于接收转换结果
        String result = "";
        // 转大写
        hexString = hexString.toUpperCase();
        // 16进制字符
        String hexDigital = "0123456789ABCDEF";
        // 将16进制字符串转换成char数组
        char[] hexs = hexString.toCharArray();
        // 能被16整除，肯定可以被2整除
        byte[] bytes = new byte[hexString.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = hexDigital.indexOf(hexs[2 * i]) * 16 + hexDigital.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        // byte[]--&gt;String
        result = new String(bytes, "UTF-8");
        return result;
    }

    //region-------------------------------------字符串分割-------------------------------------

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString 原始字符串
     * @param length      指定长度
     * @return
     */
    public static ArrayList<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString 原始字符串
     * @param length      指定长度
     * @param size        指定列表大小
     * @return
     */
    public static ArrayList<String> getStrList(String inputString, int length,
                                               int size) {
        ArrayList<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str 原始字符串
     * @param f   开始位置
     * @param t   结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        }
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    /**
     * 把String按一定长度拆分，返回ListString
     *
     * @param str
     * @param len
     * @return
     */
    public List<String> getListStr(String str, int len) {
        List<String> listStr = new ArrayList<>();
        int strLen = str.length();
        int start = 0;
        int num = len;
        String temp = null;
        while (true) {
            try {
                if (num >= strLen) {
                    temp = str.substring(start, strLen);
                } else {
                    temp = str.substring(start, num);
                }
            } catch (Exception e) {
                Lg.i("拆分完毕", "");
                break;
            }
            listStr.add(temp);
            start = num;
            num = num + len;
        }
        return listStr;
    }
    //endregion-------------------------------------字符串分割-------------------------------------
}
