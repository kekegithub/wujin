package com.hardware.ui.main;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hardware.R;
import com.hardware.ui.shop.AllShopFragment;
import com.zhan.framework.utils.PixelUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView mGridView;

    private int[] mIcons=new int[]{
            R.drawable.dp_qblm_wjjd,
            R.drawable.dp_qblm_dgdq,
            R.drawable.dp_qblm_wjlj,
            R.drawable.dp_qblm_dzqj,
            R.drawable.dp_qblm_yqyb,
            R.drawable.dp_qblm_jxsb,
            R.drawable.dp_qblm_hysb,
            R.drawable.dp_qblm_xjhg,
            R.drawable.dp_qblm_aflb,
            R.drawable.dp_qblm_xzbg,
            R.drawable.dp_qblm_cfyj,
            R.drawable.dp_qblm_azjc

    };
    private String[] mTitles=new String[]{
            "五金机电",
            "电工电器",
            "五金零件",
            "电子器件",
            "仪器仪表",
            "机械设备",
            "行业设备",
            "橡塑化工",
            "安防劳保",
            "行政办公",
            "厨房餐饮",
            "家装建材"};

    public ShopFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_shop, container, false);
        mGridView=(GridView)rootView.findViewById(R.id.grid_view);
        mGridView.setAdapter(new ShopAdapter());
        mGridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AllShopFragment.launch(getActivity());
    }

    private class ShopAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mIcons.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=new TextView(getActivity());
            }

            TextView item=(TextView)convertView;
            item.setText(mTitles[position]);
            item.setTextColor(Color.BLACK);
            item.setGravity(Gravity.CENTER);
            item.setCompoundDrawablePadding(PixelUtils.dp2px(8));
            item.setCompoundDrawablesWithIntrinsicBounds(0,mIcons[position],0,0);

            return convertView;
        }
    }

}
