package com.hardware.tools;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ToolsHelper {
    public static <T> T parseJson(String json, Class<T> beanClass) {
        T bean = null;
        try{
            bean = JSON.parseObject(json, beanClass);
        }catch (JSONException ex){
            Log.e("Utils", "fromJson error : " + ex.getMessage());
        }

        return bean;
    }
}
