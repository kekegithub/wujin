package com.hardware.api.okhttp.builder;

import java.util.Map;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String,String> params);
    OkHttpRequestBuilder addParams(String key,String val);
}
