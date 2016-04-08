package com.example.lesson_menu;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewPager extends Activity {
	int[] images = null;
	String[] titles = null;
	
	ArrayList<ImageView> imageSource = null;
	ArrayList<View> dots = null;
	TextView title = null;
	ViewPager viewPager;
	MyPagerAdapter  adapter;
	private  int currPage = 0;
	private  int oldPage = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		init();
	}
	public void init(){
		images = new int[]{
				R.drawable.a,
				R.drawable.b,
				R.drawable.c,
				R.drawable.d,
				R.drawable.e
		};
		titles = new String[]{
				"���ǵ�1��ͼƬ",
				"���ǵ�2��ͼƬ",
				"���ǵ�3��ͼƬ",
				"���ǵ�4��ͼƬ",
				"���ǵ�5��ͼƬ"
		};
		imageSource = new ArrayList<ImageView>();
		for(int i = 0; i < images.length;i++){
			ImageView image = new ImageView(this);
			image.setBackgroundResource(images[i]);
			imageSource.add(image);
		}

		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot1));
		dots.add(findViewById(R.id.dot2));
		dots.add(findViewById(R.id.dot3));
		dots.add(findViewById(R.id.dot4));
		dots.add(findViewById(R.id.dot5));

		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);

		viewPager = (ViewPager) findViewById(R.id.vp);
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		MyPageChangeListener listener = new MyPageChangeListener();
		viewPager.setOnPageChangeListener(listener);

		ScheduledExecutorService scheduled =  Executors.newSingleThreadScheduledExecutor();
		ViewPagerTask pagerTask = new ViewPagerTask();
		scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
	}

	private class MyPagerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			System.out.println("getCount");
			return images.length;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			System.out.println("isViewFromObject");
			return arg0 == arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			System.out.println("destroyItem==" + position);
			container.removeView(imageSource.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageSource.get(position));
			System.out.println("instantiateItem===" + position +"===="+container.getChildCount());
			return imageSource.get(position);
		}
	}

	private class MyPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			title.setText(titles[position]);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);
			oldPage = position;
			currPage = position;
		}
	}

	private class ViewPagerTask implements Runnable{
		@Override
		public void run() {
			currPage =(currPage+ 1)%images.length;
			handler.sendEmptyMessage(0);
		}
	}
	
	private Handler handler= new Handler(){
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(currPage);
		};
	};
}
