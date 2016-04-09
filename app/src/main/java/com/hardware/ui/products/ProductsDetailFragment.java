package com.hardware.ui.products;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hardware.R;
import com.hardware.api.ApiConstants;
import com.hardware.bean.ProductContent;
import com.hardware.bean.ProductsDetail;
import com.hardware.bean.ProductsDetailResponse;
import com.hardware.tools.ToolsHelper;
import com.hardware.view.McoyProductContentPage;
import com.hardware.view.McoyProductDetailInfoPage;
import com.hardware.view.McoySnapPageLayout;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ProductsDetailFragment extends ABaseFragment {

    private final static String ARG_KEY = "productsId";

    @ViewInject(id = R.id.flipLayout)
    McoySnapPageLayout mFlipLayout;
    @ViewInject(id = R.id.iv_back, click = "OnClick")
    ImageView mBack;

    private ProductContent content;
    private int id;
    private String district;
    private McoyProductDetailInfoPage mTopPage = null;
    private McoyProductContentPage mBottomPage = null;
    private List<ProductsDetailResponse.MessageEntity> messageList = new ArrayList<>();
    private List<ProductsDetail> tempProducts = new ArrayList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_products_detail_layout;
    }

    public static void launch(FragmentActivity activity, ProductContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, ProductsDetailFragment.class, args, false);
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
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        id = content.getId();
        district = content.getDistrict();
    }

    @Override
    public void requestData() {
        final RequestParams requestParams = new RequestParams();
        requestParams.put("id", id);
        requestParams.put("district", district);

        startRequest(ApiConstants.PRODUCTS_DETAIL, requestParams, new HttpRequestHandler() {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:

                        ProductsDetailResponse response = ToolsHelper.parseJson(result, ProductsDetailResponse.class);
                        if (response != null && response.getFlag() == 1) {
                            ProductsDetail productsDetail = new ProductsDetail();
                            productsDetail.setColor(response.getMessage().getColor());
                            productsDetail.setSize(response.getMessage().getSize());
                            productsDetail.setVersion(response.getMessage().getVersion());
                            productsDetail.setDescription(response.getMessage().getDescription());
                            productsDetail.setShopid(response.getMessage().getShopid());
                            productsDetail.setProductName(response.getMessage().getProductName());
                            productsDetail.setLogo(response.getMessage().getLogo());
                            productsDetail.setCompanyRegionName(response.getMessage().getCompanyRegionName());
                            productsDetail.setShopName(response.getMessage().getShopName());
                            productsDetail.setImgUrl(response.getMessage().getImgUrl());
                            productsDetail.setPackMark(response.getMessage().getPackMark());
                            productsDetail.setCreateDate(response.getMessage().getCreateDate());
                            productsDetail.setDeliveryMark(response.getMessage().getDeliveryMark());
                            productsDetail.setServiceMark(response.getMessage().getServiceMark());
                            productsDetail.setCommentNumber(response.getMessage().getCommentNumber());
                            productsDetail.setCommentSum(response.getMessage().getCommentSum());
                            productsDetail.setSaleCount(response.getMessage().getSaleCount());
                            productsDetail.setMarketPrice(response.getMessage().getMarketPrice());
                            tempProducts.add(productsDetail);
                        }
                        mTopPage = new McoyProductDetailInfoPage(getActivity(), LayoutInflater.from(getActivity()).inflate(R.layout.products_detail_detail_layout, null),tempProducts);
                        mBottomPage = new McoyProductContentPage(getActivity(), LayoutInflater.from(getActivity()).inflate(R.layout.products_detail_content_page, null));
                        mFlipLayout.setSnapPages(mTopPage, mBottomPage);
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

    void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }
}
