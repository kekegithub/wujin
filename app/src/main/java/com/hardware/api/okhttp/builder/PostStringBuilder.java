package com.hardware.api.okhttp.builder;

import com.hardware.api.okhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public class PostStringBuilder extends OkHttpRequestBuilder {
    private String content;
    private MediaType mediaType;

    public PostStringBuilder content(String content){
        this.content = content;
        return this;
    }
    public PostStringBuilder mediaType(MediaType mediaType){
        this.mediaType = mediaType;
        return this;
    }


    @Override
    public PostStringBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostStringBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostStringBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostStringBuilder addHeader(String key, String val) {
        if (this.headers == null){
            headers = new LinkedHashMap<>();
        }
        headers.put(key,val);
        return this;
    }

    @Override
    public RequestCall build() {
        return null;
    }
}
