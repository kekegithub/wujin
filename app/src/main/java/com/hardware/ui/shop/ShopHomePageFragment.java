package com.hardware.ui.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ShopProductsListResponse;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by WuYue on 2016/4/8.
 */
public class ShopHomePageFragment extends ABaseFragment {
    private final static String ARG_KEY = "shopId";

    private final static int SORT_ALL = 0;//全部
    private final static int SORT_BY_SALE = 1;//销售量排序
    private final static int SORT_BY_PRICE = 2;//价格排序
    private final static int SORT_BY_NEW = 3;//最新产品

    private int mShopId;
    private int shopSort = SORT_ALL;

    @ViewInject(id = R.id.shopImg)
    private ImageView mShopImg;
    @ViewInject(id = R.id.shop_name)
    private TextView mShopName;
    @ViewInject(id = R.id.shop_age)
    private TextView mShopAge;
    @ViewInject(id = R.id.shop_grade)
    private TextView mShopGrade;
    @ViewInject(id = R.id.collect_shop, click = "OnClick")
    private TextView mCollectShop;
    @ViewInject(id = R.id.shop_fans)
    private TextView mShopFans;

    @ViewInject(id = R.id.shop_home_page , click = "OnClick")
    private TextView mShopHomePage;
    @ViewInject(id = R.id.all_products , click = "OnClick")
    private TextView mAllProducts;
    @ViewInject(id = R.id.new_products , click = "OnClick")
    private TextView mNewProducts;

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
        refreshViews();
    }

    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("shopId", mShopId);
        requestParams.put("shopSort", shopSort);
        requestParams.put("page", 1);
        startRequest(ApiConstants.SHOP_PRODUCTS_LIST, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        ShopProductsListResponse response = ToolsHelper.parseJson(result, ShopProductsListResponse.class);
                        if (response != null && response.getFlag() == 1) {
                            mShopName.setText(response.getShops().getShopName());
                            mShopAge.setText(getTimeSpanStr(response.getShops().getCreateDate()));
                            mShopGrade.setText(response.getShops().getGradName());
                            mShopFans.setText(response.getShops().getAttention() + "\n粉丝");
                        }

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

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.shop_home_page:
                if(shopSort!=SORT_ALL){
                    shopSort=SORT_ALL;
                    refreshViews();
                }
                break;
            case R.id.all_products:
                if(shopSort!=SORT_BY_SALE){
                    shopSort=SORT_BY_SALE;
                    refreshViews();
                }
                break;
            case R.id.new_products:
                if(shopSort!=SORT_BY_NEW){
                    shopSort=SORT_BY_NEW;
                    refreshViews();
                }
                break;
            case R.id.collect_shop:
                break;
        }
    }

    private void refreshViews(){
        switch (shopSort){
            case SORT_ALL:
                setTabSelected(mShopHomePage,true,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,false,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);
                break;
            case SORT_BY_SALE:
                setTabSelected(mShopHomePage,false,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);
                break;
            case SORT_BY_PRICE:
                setTabSelected(mShopHomePage,false,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,true,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,false,R.drawable.new_products_selected,R.drawable.new_products_normal);
                break;
            case SORT_BY_NEW:
                setTabSelected(mShopHomePage,false,R.drawable.shop_home_sellected,R.drawable.shop_home_normal);
                setTabSelected(mAllProducts,false,R.drawable.all_products_selected,R.drawable.all_products_normal);
                setTabSelected(mNewProducts,true,R.drawable.new_products_selected,R.drawable.new_products_normal);
                break;
        }
    }


    private void setTabSelected(TextView tab,boolean isSelelcted,int selDrawableRes,int unSelDrawableRes){
        int selColor=getResources().getColor(R.color.blue);
        int unSelColor=getResources().getColor(R.color.text_color);
        if(isSelelcted){
            tab.setTextColor(selColor);
            tab.setBackgroundResource(R.drawable.bg_dark_blue_underline);
            tab.setCompoundDrawablesWithIntrinsicBounds(0,selDrawableRes,0,0);
        }else{
            tab.setTextColor(unSelColor);
            tab.setBackgroundResource(R.drawable.bg_white_small);
            tab.setCompoundDrawablesWithIntrinsicBounds(0,unSelDrawableRes,0,0);
        }
    }

    private String getTimeSpanStr(String startTime) {
        String diff = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = df.parse(startTime);
            Calendar calendarNow = Calendar.getInstance();
            Calendar calendarCreateTime = Calendar.getInstance();
            calendarCreateTime.setTime(date);

            long between = (calendarNow.getTimeInMillis() - calendarCreateTime.getTimeInMillis()) / 1000;//除以1000是为了转换成秒
            long days = between / (24 * 3600);
            diff = days + "天";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }
}
