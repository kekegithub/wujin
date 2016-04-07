package com.hardware.api.bean.home;

import com.alibaba.fastjson.JSON;
import com.hardware.api.okhttp.callback.Callback;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Response;

/**
 * Created by zhoupenglei on 16/4/1.
 */
public abstract class HomeProductsCallback extends Callback<HomeProductsRes> {
    @Override
    public HomeProductsRes parseNetworkResponse(Response response) throws IOException {
        String resStr = response.body().string();
        HomeProductsRes homeProductsRes = JSON.parseObject(resStr,HomeProductsRes.class);
        return homeProductsRes;
    }


}
