package com.hardware.api.okhttp.callback;

import okhttp3.Response;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public abstract class StringCallback extends Callback<String>  {

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}
