package com.hardware.ui.products;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.HomeProductsBean;
import com.hardware.bean.MoreDiscountSaleResponse;
import com.hardware.tools.ToolsHelper;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class MoreDiscountSaleFragment extends ABaseFragment {
    private final static String ARG_KEY = "morediscount";

    public static void launch(Activity from,String mTitle) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, mTitle);
        FragmentContainerActivity.launch(from, MoreDiscountSaleFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = savedInstanceState == null ? (String) getArguments().getSerializable(ARG_KEY)
                : (String) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mTitle);
    }


    private final static int PAGE_SIZE=10;

    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;
    private String mTitle ;

    private boolean QueryMore=false;
    private boolean HasMoreData=true;

    private LayoutInflater mInflater;

    private List<Product> mProducts=new LinkedList<>();
    private ProductAdapter mAdpater=new ProductAdapter();

    private DisplayImageOptions options;

    private Handler mHandler=new Handler();

    private class Product{
        private int id;
        private String imgUrl;
        private String ProductName;
        private double MarketPrice;
        private double MinSalePrice;
        private String productCode;
        private String MeasureUnit;
        private int SaleCounts;
        private String CompanyAddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            MarketPrice = marketPrice;
        }

        public double getMinSalePrice() {
            return MinSalePrice;
        }

        public void setMinSalePrice(double minSalePrice) {
            MinSalePrice = minSalePrice;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getMeasureUnit() {
            return MeasureUnit;
        }

        public void setMeasureUnit(String measureUnit) {
            MeasureUnit = measureUnit;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public void setSaleCounts(int saleCounts) {
            SaleCounts = saleCounts;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            CompanyAddress = companyAddress;
        }
    }
    @Override
    protected int inflateContentView() {
        return R.layout.fragment_more_discount_sale;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle(mTitle);
        mInflater=inflater;
        options= buldDisplayImageOptions();
        mListView = mPullToRefreshListView.getRefreshableView();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.getLoadingLayoutProxy(true,false).setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true,false).setPullLabel("下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true,false).setReleaseLabel("释放开始刷新");
        intPullUpLable(true);
        mPullToRefreshListView.setAdapter(mAdpater);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()){
                    //下拉刷新
                    QueryMore=false;
                    intPullUpLable(true);
                    requestData();
                }else{
                    //上拉加载更多
                    if(!HasMoreData){
                        onRefreshComplete();
                        return;
                    }
                    QueryMore=true;
                    requestData();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
    }

    @Override
    public void requestData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("Page", getNextPage());

        startRequest(ApiConstants.MORE_DISCOUNT_SALE_LIST, requestParams, new BaseHttpRequestTask<MoreDiscountSaleResponse>() {
            @Override
            public MoreDiscountSaleResponse parseResponseToResult(String content) {
                return ToolsHelper.parseJson(content, MoreDiscountSaleResponse.class);
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                onRefreshComplete();
            }

            @Override
            protected void onSuccess(MoreDiscountSaleResponse response) {
                super.onSuccess(response);
                if (!QueryMore) {
                    mProducts.clear();
                }
                if (response != null && response.getFlag() == 1) {
                    List<Product> tempProducts = new LinkedList<>();
                    for (MoreDiscountSaleResponse.MessageBean.RowsBean responseItem : response.getMessage().getRows()) {
                        Product product = new Product();
                        product.setId(responseItem.getId());
                        product.setImgUrl(responseItem.getImgUrl());
                        product.setMarketPrice(responseItem.getMarketPrice());
                        product.setMinSalePrice(responseItem.getMinSalePrice());
                        product.setProductName(responseItem.getProductName());
                        product.setSaleCounts(responseItem.getSaleCounts());
                        product.setCompanyAddress(responseItem.getCompanyAddress());
                        tempProducts.add(product);
                    }

                    if (tempProducts.size() == PAGE_SIZE) {
                        HasMoreData = true;
                    } else {
                        HasMoreData = false;
                    }

                    intPullUpLable(HasMoreData);
                    mProducts.addAll(tempProducts);
                    mAdpater.notifyDataSetChanged();
            }
            }
        }, HttpRequestUtils.RequestType.GET);
    }

    private int getNextPage(){
        if(!QueryMore){
            return 1;
        }
        int pageIndex=1+mProducts.size()/PAGE_SIZE;
        return pageIndex;
    }

    private void onRefreshComplete(){
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable,50);
    }

    private Runnable mRefreshCompleteRunnable=new Runnable(){
        @Override
        public void run() {
            mPullToRefreshListView.onRefreshComplete();
        }
    };

    public class ProductAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return mProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.more_discount_sale_list_item,null);
                holder=new ViewHolder();
                holder.image=(ImageView)convertView.findViewById(R.id.image);
                holder.name=(TextView)convertView.findViewById(R.id.name);
                holder.local=(TextView)convertView.findViewById(R.id.local);
                holder.salesNum =(TextView)convertView.findViewById(R.id.sale);
                holder.newPrice=(TextView)convertView.findViewById(R.id.new_price);
                holder.oldPrice=(TextView)convertView.findViewById(R.id.old_price);
                holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//删除线
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }

            Product product=mProducts.get(position);

            holder.name.setText(product.getProductName());
            holder.local.setText(product.getCompanyAddress());
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("￥ 00.00");
            holder.oldPrice.setText(df.format(product.getMarketPrice()));
            holder.newPrice.setText(df.format(product.getMinSalePrice()));
            holder.salesNum.setText(String.format("成交%d笔", product.getSaleCounts()));
            String imgUrl= ApiConstants.IMG_BASE_URL+product.getImgUrl();
            ImageLoader.getInstance().displayImage(imgUrl, holder.image,options);

            return convertView;
        }
    }

    private void intPullUpLable(boolean canLoadMore){
        if(canLoadMore){
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载");
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setPullLabel("上拉加载更多");
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setReleaseLabel("释放开始加载");
        }else{
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setPullLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false,true).setReleaseLabel("没有更多数据了");
        }
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView local;
        TextView salesNum;
        TextView newPrice;
        TextView oldPrice;
    }

    public DisplayImageOptions buldDisplayImageOptions(){
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .considerExifParams(true)
                .build();
    }
}
