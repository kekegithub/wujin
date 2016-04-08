package com.hardware.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hardware.R;

/**
 * Created by Administrator on 2016/4/8.
 */
public class McoyProductDetailInfoPage implements McoySnapPageLayout.McoySnapPage{

    private Context context;
    private View rootView = null;
    private MyScrollView myScrollView = null;


    public McoyProductDetailInfoPage(FragmentActivity context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        myScrollView = (MyScrollView) this.rootView
                .findViewById(R.id.product_scrollview);
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
}
