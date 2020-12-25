package org.wzp.oauth2.util;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/21 13:08
 */
public class StringUtil {


    /**
     * 检查参数是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }


    /**
     * 检查参数是否为空 排除== "" 的验证
     *
     * @param str
     * @return
     */
    public static boolean isEmptyStr(@Nullable Object str) {
        return (str == null);
    }


    /**
     * 检查 list 是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmptyList(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }


    /**
     * 检查 map 是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmptyMap(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }


    /**
     * 获取指定长度之后部分
     *
     * @param str
     * @param beginIndex
     * @return
     */
    public static String subString(String str, int beginIndex) {
        if (str.length() - beginIndex > 0) {
            return str.substring(beginIndex);
        }
        return "";
    }


    /**
     * 获取指定部分
     *
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String subSpecString(String str, int beginIndex, int endIndex) {
        return str.substring(beginIndex, endIndex);
    }


    /**
     * 获取指定分隔符之前部分（不包含分隔符）
     *
     * @param str       需要分割的字符串
     * @param separator 分隔符
     * @param index     偏移量>=0
     * @return
     */
    public static String strPrefix(String str, String separator, Integer index) {
        if (!str.contains(separator)) {
            return str;
        }
        if (index <= 0) {
            return str.substring(0, str.lastIndexOf(separator));
        }
        return str.substring(0, str.lastIndexOf(separator) + index);

    }


    /**
     * 获取指定分隔符之后部分(包含分隔符)
     *
     * @param str       需要分割的字符串
     * @param separator 分隔符
     * @param index     偏移量>=0
     * @return
     */
    public static String strSuffix(String str, String separator, Integer index) {
        if (!str.contains(separator)) {
            return "";
        }
        if (index <= 0) {
            return str.substring(str.lastIndexOf(separator));
        }
        return str.substring(str.lastIndexOf(separator) + index);
    }


}
