package com.hardware.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ProductsDetail;
import com.hardware.ui.shop.ShopHomePageFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.support.inject.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class McoyProductDetailInfoPage implements McoySnapPageLayout.McoySnapPage, View.OnClickListener {

    //view
    private ImageView mProductHead;
    private TextView mProductName ;
    private TextView mProductsWholesale;
    private TextView mProductsWholesalePrice ;
    private TextView mProductsExpress ;
    private TextView mPrductsSalesVolume;
    private TextView mProductsPlace ;
    private TextView mProductsCommentNum;
    private TextView mProductsAppriceNum ;
    private RatingBar mProductsRatingbar ;
    private ImageView mProductsDetailLogo;
    private TextView mProductShopName ;
    private TextView mProductDetailYear ;
    private TextView mProductDetailEnterShop ;


    private Context context;
    private View rootView = null;
    private MyScrollView myScrollView = null;
    private List<ProductsDetail> tempProducts = new ArrayList<>();


    public McoyProductDetailInfoPage(FragmentActivity context, View rootView, List<ProductsDetail> tempProducts) {
        this.context = context;
        this.rootView = rootView;
        myScrollView = (MyScrollView) this.rootView
                .findViewById(R.id.product_scrollview);
        this.tempProducts = tempProducts ;
        initView();
    }

    private void initView() {
        mProductHead = (ImageView) getRootView().findViewById(R.id.products_detail_head_bg);
        String imgUrl= ApiConstants.IMG_BASE_URL+ tempProducts.get(0).getImgUrl();
        ImageLoader.getInstance().displayImage(imgUrl, mProductHead);
        mProductName = (TextView) getRootView().findViewById(R.id.products_detail_productname);
        mProductName.setText(tempProducts.get(0).getProductName());
        mProductsWholesale = (TextView)getRootView().findViewById(R.id.products_wholesale);
        //mProductsWholesale.setText(tempProducts.get(0).get);
        mProductsWholesalePrice = (TextView)getRootView().findViewById(R.id.products_wholesale_price);
        mProductsWholesalePrice.setText("￥"+tempProducts.get(0).getMarketPrice()+"");
        mProductsExpress = (TextView) getRootView().findViewById(R.id.products_detail_express);
        mProductsExpress.setText("快递 ￥"+tempProducts.get(0).getDeliveryMark());
        mPrductsSalesVolume = (TextView) getRootView().findViewById(R.id.products_detail_sales_volume);
        mPrductsSalesVolume.setText("成交 "+tempProducts.get(0).getSaleCount()+"个");
        mProductsPlace= (TextView) getRootView().findViewById(R.id.products_detail_place);
        mProductsPlace.setText("发货: "+tempProducts.get(0).getCompanyRegionName());
        mProductsCommentNum = (TextView) getRootView().findViewById(R.id.products_detail_comment_num);
        mProductsCommentNum.setText(tempProducts.get(0).getCommentNumber()+"人已评价");
        mProductsAppriceNum = (TextView)getRootView().findViewById(R.id.tv_products_detail_appraise_numbers);
        mProductsAppriceNum.setText(tempProducts.get(0).getCommentSum()+"");
        mProductsRatingbar = (RatingBar) getRootView().findViewById(R.id.products_detail_ratingbar);
        mProductsRatingbar.setRating((float)tempProducts.get(0).getServiceMark());

        mProductsDetailLogo = (ImageView) getRootView().findViewById(R.id.products_detail_shopurl);
        String shopimgUrl= ApiConstants.IMG_BASE_URL+ tempProducts.get(0).getLogo();
        ImageLoader.getInstance().displayImage(shopimgUrl, mProductsDetailLogo);

        mProductShopName = (TextView)getRootView().findViewById(R.id.tv_products_detail_shopname);
        mProductShopName.setText(tempProducts.get(0).getShopName());
        mProductDetailYear = (TextView) getRootView().findViewById(R.id.tv_products_detail_year);

        mProductDetailEnterShop = (TextView) getRootView().findViewById(R.id.products_detail_enter_shop);
        mProductDetailEnterShop.setOnClickListener(this);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public boolean isAtTop() {
        return true;
    }

    @Override
    public boolean isAtBottom() {
        int scrollY = myScrollView.getScrollY();
        int height = myScrollView.getHeight();
        int scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();

        if ((scrollY + height) >= scrollViewMeasuredHeight) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.products_detail_enter_shop:
                ShopHomePageFragment.launch((Activity) context, tempProducts.get(0).getShopid(), tempProducts.get(0).getLogo());
                break;
        }
    }
}
