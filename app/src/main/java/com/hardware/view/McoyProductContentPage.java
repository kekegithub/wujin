package com.hardware.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/4/8.
 */
public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {

    private Context context;
    private View rootView = null;

    public McoyProductContentPage(FragmentActivity context, View rootView) {
        this.context = context;
        this.rootView = rootView;
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
        return false;
    }
}
