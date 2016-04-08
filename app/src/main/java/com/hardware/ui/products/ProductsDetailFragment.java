package com.hardware.ui.products;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.hardware.R;
import com.hardware.bean.ProductContent;
import com.hardware.view.McoyProductContentPage;
import com.hardware.view.McoyProductDetailInfoPage;
import com.hardware.view.McoySnapPageLayout;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ProductsDetailFragment extends ABaseFragment{

    private final static String ARG_KEY = "productsId";

    @ViewInject(id = R.id.flipLayout)
    McoySnapPageLayout mFlipLayout ;

    private ProductContent content;
    private int id ;
    private String district ;
    private McoyProductDetailInfoPage mTopPage = null ;
    private McoyProductContentPage mBottomPage = null ;

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

        mTopPage =  new McoyProductDetailInfoPage(getActivity(),inflater.inflate(R.layout.products_detail_detail_layout, null));
        mBottomPage = new McoyProductContentPage(getActivity(),inflater.inflate(R.layout.products_detail_content_page, null));
        mFlipLayout.setSnapPages(mTopPage, mBottomPage);
    }

    @Override
    public void requestData() {

    }
}
