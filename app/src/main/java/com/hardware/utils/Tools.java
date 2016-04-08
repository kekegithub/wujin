package com.hardware.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hardware.bean.HomeProductsBean;

/**
 * Created by Administrator on 2016/4/7.
 */
public class Tools {

    public static <T extends HomeProductsBean> T parseJson(String json, Class<T> beanClass) {
        T bean = null;
        try{
            bean = JSON.parseObject(json, beanClass);
        }catch (JSONException ex){
            Log.e("Utils", "fromJson error : " + ex.getMessage());
        }

        return bean;
    }
}
