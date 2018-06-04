package com.gjf.utils;

/**
 * @Author: GJF
 * @Date : 2018/06/02
 * Time   : 11:33
 */
public class StringUtil {

    public static boolean isBlank(String str){
        return str == null || str.isEmpty() || str.replaceAll(" ", "").isEmpty();
    }

    public static boolean isBlank(String... strs){
        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }
}
