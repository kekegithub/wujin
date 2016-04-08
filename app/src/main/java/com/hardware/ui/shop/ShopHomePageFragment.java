package com.hardware.ui.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

/**
 * Created by WuYue on 2016/4/8.
 */
public class ShopHomePageFragment extends ABaseFragment {
    private final static String ARG_KEY = "shopId";

    private int mShopId;

    @ViewInject(id = R.id.shopImg)
    private ImageView mShopImg;
    @ViewInject(id = R.id.shop_name)
    private TextView mShopName;
    @ViewInject(id = R.id.shop_age)
    private TextView mShopAge;
    @ViewInject(id = R.id.shop_grade)
    private TextView mShopGrade;
    @ViewInject(id = R.id.collect_shop)
    private TextView mCollectShop;
    @ViewInject(id = R.id.shop_fans)
    private TextView mShopFans;

    public static void launch(Activity from, int shopId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, shopId);
        FragmentContainerActivity.launch(from, ShopHomePageFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY)
                : (int) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_shop_home_page;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("店铺首页");
    }

    @Override
    public void requestData() {
        RequestParams requestParams=new RequestParams();
        requestParams.put("id",mShopId);
        startRequest(ApiConstants.SHOP_BASICALLY, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        break;
                    case canceled:
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }

            }
        }, HttpRequestUtils.RequestType.GET);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mShopId);
    }
}
