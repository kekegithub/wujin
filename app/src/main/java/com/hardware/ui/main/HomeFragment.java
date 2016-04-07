package com.hardware.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.api.bean.home.HomeProductsCallback;
import com.hardware.api.bean.home.HomeProductsRes;
import com.hardware.api.okhttp.OkHttpUtils;
import com.hardware.api.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;


public class HomeFragment extends Fragment {


    @Bind(R.id.http_test_btn)
    Button httpTestBtn;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.http_test_btn)
    public void onClick() {
        OkHttpUtils
                .post()
                .url(ApiConstants.MOBILE_HOME_PRODUCTS_LIST)
                .build()
                .execute(new HomeProductsCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(HomeProductsRes response) {
                        Log.i("******",response.getMessage().getTotal()+"");
                    }
                });
    }


}
