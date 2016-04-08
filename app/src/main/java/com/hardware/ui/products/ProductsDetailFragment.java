package com.hardware.ui.products;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.hardware.R;
import com.hardware.bean.ProductContent;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ProductsDetailFragment extends ABaseFragment{

    private final static String ARG_KEY = "productsId";

    private ProductContent content;
    private int id ;
    private String district ;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_products_detail_layout;
    }

    public static void launch(FragmentActivity activity, ProductContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, ProductsDetailFragment.class, args);
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
        getActivity().setTitle("商品详情");
        id = content.getId();
        district = content.getDistrict();
    }
}
