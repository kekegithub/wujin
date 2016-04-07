package com.hardware.api.okhttp.utils;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public class Exceptions {
    public static void illegalArgument(String msg,Object... params){
        throw new IllegalArgumentException(String.format(msg,params));
    }
}
