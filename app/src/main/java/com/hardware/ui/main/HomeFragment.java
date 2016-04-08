package com.hardware.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.HomeProductsBean;
import com.hardware.tools.ToolsHelper;
import com.hardware.view.MyGridView;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends ABaseFragment {

    @ViewInject(id = R.id.viewpager)
    ViewPager mViewPager;
    @ViewInject(id = R.id.home_gridView)
    MyGridView mGridView ;
    @ViewInject(id = R.id.home_special_offer_gridView)
    MyGridView mSaleGridView ;

    private ArrayList<ImageView> mImageSource;
    private int[] mImages = {R.drawable.home_view_anim_banner1, R.drawable.home_view_anim_banner2, R.drawable.home_view_anim_banner3};
    private int[] mImageIcon = { R.drawable.home_popularity_brand, R.drawable.home_user_buy,
            R.drawable.home_spread, R.drawable.home_consult,R.drawable.home_repair,
            R.drawable.home_build, R.drawable.home_advertise,R.drawable.home_more
           };
    private String[] mIconName = { "人气品牌", "用户求购", "商家推广", "行业资讯", "便民维修", "便民施工", "专业招聘", "更多"};

    private ArrayList<View> mAdList;
    private List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
    private List<HomeProductsBean.MessageEntity.RowsEntity> mSaleList = new ArrayList<>();//折扣特卖
    private List<HomeProductsBean.ProTypeEntity.RowsEntity> mProTypeList = new ArrayList<>();//热销单品


    private HomePagerAdapter mHomeAdapter;
    private SimpleAdapter mSimpleAdapter;
    private HomeSaleAdapter mSaleAdapter ;
    private int currPage = 0;
    private int oldPage = 0;


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        initViewPager();
        getData();

        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList, R.layout.home_gridview_item, from, to);
        mGridView.setAdapter(mSimpleAdapter);


    }

    @Override
    public void requestData() {
        startRequest(ApiConstants.MOBILE_HOME_PRODUCTS_LIST, null, new BaseHttpRequestTask<HomeProductsBean>() {
            @Override
            public HomeProductsBean parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, HomeProductsBean.class);
            }

            @Override
            protected void onSuccess(HomeProductsBean responseBean) {
                super.onSuccess(responseBean);
                if (responseBean != null) {
                    mSaleList = responseBean.getMessage().getRows();
                    mProTypeList = responseBean.getProType().getRows();
                    mSaleGridView.setAdapter(new HomeSaleAdapter(mSaleList));
                }
            }

        });
    }

    private List<Map<String, Object>> getData(){
        for(int i=0;i<mImageIcon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", mImageIcon[i]);
            map.put("text", mIconName[i]);
            mDataList.add(map);
        }
        return mDataList;
    }


    private void initViewPager() {
        mImageSource = new ArrayList<ImageView>();
        for (int i = 0; i < mImages.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setBackgroundResource(mImages[i]);
            mImageSource.add(image);
        }

        mAdList = new ArrayList<View>();
        mAdList.add(findViewById(R.id.dot1));
        mAdList.add(findViewById(R.id.dot2));
        mAdList.add(findViewById(R.id.dot3));

        mHomeAdapter = new HomePagerAdapter();
        mViewPager.setAdapter(mHomeAdapter);
        MyPageChangeListener listener = new MyPageChangeListener();
        mViewPager.setOnPageChangeListener(listener);

        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        ViewPagerTask pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
    }

    private class HomePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageSource.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageSource.get(position));
            return mImageSource.get(position);
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mAdList.get(position).setBackgroundResource(R.drawable.dot_focused);
            mAdList.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
            oldPage = position;
            currPage = position;
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currPage = (currPage + 1) % mImages.length;
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currPage);
        }
    };

    private class HomeSaleAdapter extends BaseAdapter{

        private List<HomeProductsBean.MessageEntity.RowsEntity> saleList = new ArrayList<>();
        public HomeSaleAdapter(List<HomeProductsBean.MessageEntity.RowsEntity> saleList) {
            this.saleList = saleList ;
        }

        @Override
        public int getCount() {
            return saleList.size();
        }

        @Override
        public Object getItem(int position) {
            return saleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_sale_item,null);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            return convertView;
        }
    }

    static class ViewHolder{

    }

}
