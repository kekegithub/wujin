package com.hardware.api.okhttp.builder;

import com.hardware.api.okhttp.request.RequestCall;

import java.util.Map;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public abstract class OkHttpRequestBuilder {
    protected String url;
    protected Object tag;
    protected Map<String,String> headers;
    protected Map<String,String> params;

    public abstract OkHttpRequestBuilder url(String url);
    public abstract OkHttpRequestBuilder tag(Object tag);
    public abstract OkHttpRequestBuilder headers(Map<String,String> headers);
    public abstract OkHttpRequestBuilder addHeader(String key, String val);
    public abstract RequestCall build();
}
