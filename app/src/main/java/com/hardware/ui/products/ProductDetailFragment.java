package com.hardware.ui.products;

/**
 * Created by Administrator on 2016/4/11.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ProductContent;
import com.hardware.bean.ProductsDetailResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.ui.shop.ShopHomePageFragment;
import com.hardware.view.RatingBar;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;


/**
 * Created by Administrator on 2016/4/11.
 */
public class ProductDetailFragment extends ABaseFragment {
    private final static String ARG_KEY = "productId";

    //第一页
    @ViewInject(id = R.id.products_detail_head_bg)
    ImageView mProductHead;
    @ViewInject(id = R.id.products_detail_productname)
    TextView mProductName ;
    @ViewInject(id = R.id.products_wholesale)
    TextView mProductsWholesale ;
    @ViewInject(id = R.id.products_wholesale_price)
    TextView mProductsWholesalePrice ;
    @ViewInject(id = R.id.products_detail_express)
    TextView mProductsExpress ;
    @ViewInject(id = R.id.products_detail_sales_volume)
    TextView mPrductsSalesVolume;
    @ViewInject(id = R.id.products_detail_place)
    TextView mProductsPlace ;
    @ViewInject(id = R.id.products_detail_comment_num)
    TextView mProductsCommentNum ;
    @ViewInject(id = R.id.tv_products_detail_appraise_numbers)
    TextView mProductsAppriceNum;
    @ViewInject(id = R.id.products_detail_ratingbar)
    RatingBar mProductsRatingbar ;
    @ViewInject(id = R.id.products_detail_shopurl)
    ImageView mProductsDetailLogo ;
    @ViewInject(id = R.id.tv_products_detail_shopname)
    TextView mProductShopName;
    @ViewInject(id = R.id.tv_products_detail_year)
    TextView mProductDetailYear ;
    @ViewInject(id = R.id.products_detail_enter_shop)
    TextView mProductDetailEnterShop ;

    //第二页
    @ViewInject(id = R.id.detail_picture, click = "OnClick")
    TextView mDetailPicture ;
    @ViewInject(id = R.id.detail_products, click = "OnClick")
    TextView mDetailProducts ;
    @ViewInject(id = R.id.detail_recommend, click = "OnClick")
    TextView mDetailRecommend ;
    @ViewInject(id = R.id.detail_picture_framelayout)
    FrameLayout mPictureFrameLayout ;
    @ViewInject(id = R.id.detail_products_framelayout)
    FrameLayout mProductFrameLayout ;
    @ViewInject(id = R.id.detail_recommend_framelayout)
    FrameLayout mRecommendFrameLayout ;
    @ViewInject(id = R.id.detail_picture_framelayout_detail)
    TextView mProductDetailPrictue ;
    @ViewInject(id = R.id.detail_recommend_listview)
    ListView mRecommengListView;


    private ProductContent content;
    private int id;
    private String district;
    private int RecommendFrameLayoutfalg = 0 ;



    public static void launch(FragmentActivity activity, ProductContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, ProductDetailFragment.class, args, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (ProductContent) getArguments().getSerializable(ARG_KEY)
                : (ProductContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }


    @Override
    protected int inflateContentView() {
        return R.layout.fragment_product_detail_layout;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        id = content.getId();
        district = content.getDistrict();
    }

    @Override
    public void requestData() {

        if(RecommendFrameLayoutfalg == 0){
            final RequestParams requestParams = new RequestParams();
            requestParams.put("id", id);
            requestParams.put("district", district);

            startRequest(ApiConstants.PRODUCTS_DETAIL, requestParams, new HttpRequestHandler() {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:

                            final ProductsDetailResponse response = ToolsHelper.parseJson(result, ProductsDetailResponse.class);
                            if (response != null && response.getFlag() == 1) {
                                String imgUrl= ApiConstants.IMG_BASE_URL+ response.getMessage().getImgUrl();
                                ImageLoader.getInstance().displayImage(imgUrl, mProductHead);
                                mProductName.setText(response.getMessage().getProductName());
                                //mProductsWholesale.setText(tempProducts.get(0).get);
                                mProductsWholesalePrice.setText("￥"+response.getMessage().getMarketPrice()+"");
                                mProductsExpress.setText("快递 ￥"+response.getMessage().getDeliveryMark());
                                mPrductsSalesVolume.setText("成交 "+response.getMessage().getSaleCount()+"个");
                                mProductsPlace.setText("发货: "+response.getMessage().getCompanyRegionName());
                                mProductsCommentNum.setText(response.getMessage().getCommentNumber()+"人已评价");
                                mProductsAppriceNum.setText(response.getMessage().getCommentSum()+"");
                                mProductsRatingbar.setRating((float) response.getMessage().getServiceMark());
                                String shopimgUrl= ApiConstants.IMG_BASE_URL+ response.getMessage().getLogo();
                                ImageLoader.getInstance().displayImage(shopimgUrl, mProductsDetailLogo);
                                mProductShopName.setText(response.getMessage().getShopName());

                                mProductDetailEnterShop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ShopHomePageFragment.launch(getActivity(), response.getMessage().getShopid(), response.getMessage().getLogo());
                                    }
                                });

                                mProductDetailPrictue.setText(response.getMessage().getDescription());

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
        }else{
            RequestParams requestParams = new RequestParams();
            requestParams.put("shopid", id);
            requestParams.put("Page", getNextPage());



        }
    }


    void OnClick(View view){
        switch (view.getId()){
            case R.id.detail_picture:
                mDetailPicture.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailPicture.setTextColor(getResources().getColor(R.color.blue));

                mDetailProducts.setBackgroundColor(Color.WHITE);
                mDetailProducts.setTextColor(getResources().getColor(R.color.text_color));

                mDetailRecommend.setBackgroundColor(Color.WHITE);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.text_color));

                mPictureFrameLayout.setVisibility(View.VISIBLE);
                mProductFrameLayout.setVisibility(View.GONE);
                mRecommendFrameLayout.setVisibility(View.GONE);
                break;
            case R.id.detail_products:
                mDetailProducts.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailProducts.setTextColor(getResources().getColor(R.color.blue));

                mDetailPicture.setBackgroundColor(Color.WHITE);
                mDetailPicture.setTextColor(getResources().getColor(R.color.text_color));

                mDetailRecommend.setBackgroundColor(Color.WHITE);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.text_color));

                mProductFrameLayout.setVisibility(View.VISIBLE);
                mPictureFrameLayout.setVisibility(View.GONE);
                mRecommendFrameLayout.setVisibility(View.GONE);

                break;
            case R.id.detail_recommend:
                mDetailRecommend.setBackgroundResource(R.drawable.bg_dark_blue_underline);
                mDetailRecommend.setTextColor(getResources().getColor(R.color.blue));

                mDetailPicture.setBackgroundColor(Color.WHITE);
                mDetailPicture.setTextColor(getResources().getColor(R.color.text_color));

                mDetailProducts.setBackgroundColor(Color.WHITE);
                mDetailProducts.setTextColor(getResources().getColor(R.color.text_color));

                mRecommendFrameLayout.setVisibility(View.VISIBLE);
                mPictureFrameLayout.setVisibility(View.GONE);
                mProductFrameLayout.setVisibility(View.GONE);

                RecommendFrameLayoutfalg = 1 ;
                requestData();
                break;
        }
    }


}
