package com.gb.cwsm.engineer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.adapter.MyViewpagerAdapter;

public class MyMessageFragment extends Fragment implements OnClickListener {

	private LayoutInflater inflater;
	private View mainView, yuyueV, newV, sysV;
	private TextView yuyueTV, newordTV, sysmsgTV;
	private ViewPager viewPager;
	private List<View> views;
	private MyViewpagerAdapter adapter;
	private Resources res;
	private Drawable drawable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		inflater = LayoutInflater.from(getActivity());
		mainView = inflater.inflate(R.layout.message_center, null);
		initview();
		return mainView;
	}

	private void initview() {
		initviewpager();
		res = getActivity().getResources();
		yuyueTV = (TextView) mainView.findViewById(R.id.msgcenter_yuyueorder);
		newordTV = (TextView) mainView.findViewById(R.id.msgcenter_neworder);
		sysmsgTV = (TextView) mainView.findViewById(R.id.msgcenter_sysmsg);

		yuyueTV.setOnClickListener(this);
		newordTV.setOnClickListener(this);
		sysmsgTV.setOnClickListener(this);
	}

	private void initviewpager() {
		views = new ArrayList<View>(3);
		viewPager = (ViewPager) mainView.findViewById(R.id.mascen_viewpager);
		yuyueV = inflater.inflate(R.layout.msgcenter_yuyueorder_view, null);
		newV = inflater.inflate(R.layout.msgcenter_neworder_view, null);
		sysV = inflater.inflate(R.layout.msgcenter_systemmsg_view, null);
		views.add(yuyueV);
		views.add(newV);
		views.add(sysV);
		adapter = new MyViewpagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(1);
		pagersetonchang();
	}

	private void pagersetonchang() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					clickTvColor(yuyueTV, R.drawable.yuyue_blue32);
					break;
				case 1:
					clickTvColor(newordTV, R.drawable.neworder_blue32);
					break;
				case 2:
					clickTvColor(sysmsgTV, R.drawable.msg_blue32);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.msgcenter_yuyueorder:
			clickTvColor(yuyueTV, R.drawable.yuyue_blue32);
			viewPager.setCurrentItem(0);
			break;
		case R.id.msgcenter_neworder:
			clickTvColor(newordTV, R.drawable.neworder_blue32);
			viewPager.setCurrentItem(1);
			break;
		case R.id.msgcenter_sysmsg:
			clickTvColor(sysmsgTV, R.drawable.msg_blue32);
			viewPager.setCurrentItem(2);
			break;

		default:
			break;
		}

	}

	private void clickTvColor(TextView tv, int id) {
		coverview();
		setTextColorDrawble(tv, id, "07B6F3");
	}

	private void coverview() {
		setTextColorDrawble(yuyueTV, R.drawable.yuyue_hui32, "bdbdbd");
		setTextColorDrawble(newordTV, R.drawable.neworder_hui32, "bdbdbd");
		setTextColorDrawble(sysmsgTV, R.drawable.msg_hui32, "bdbdbd");
	}

	private void setTextColorDrawble(TextView tv, int id, String color) {
		drawable = res.getDrawable(id);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tv.setCompoundDrawables(null, drawable, null, null);
		tv.setTextColor(Color.parseColor("#ff" + color));
	}

}
