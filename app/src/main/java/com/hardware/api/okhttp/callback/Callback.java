package com.hardware.api.okhttp.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhoupenglei on 16/3/31.
 */
public abstract class Callback<T> {
    public void onBefore(Request request){

    }
    public void onAfter(){

    }
    public void inProgress(float progress){

    }
    public abstract T parseNetworkResponse(Response response)throws Exception;
    public abstract void onError(Call call,Exception e);
    public abstract void onResponse(T response);

    public static Callback CALLBACK_DEFAULT = new Callback() {
        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    };
}
