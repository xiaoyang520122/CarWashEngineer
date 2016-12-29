package com.gb.cwsm.engineer.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.adapter.MyViewpagerAdapter;

public class CustomerPayActivity extends BaseActivity implements OnClickListener {
	
	private ViewPager vPager;
	private View view1,view2,view3;
	private List<View> views;
	private LayoutInflater inflater;
	private RadioGroup radioGroup;
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "代充值", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.custmoer_pay);
		initview();
	}

	private void initview() {
		initviews();
		vPager=(ViewPager) findViewById(R.id.customer_viewpager);
		
		vPager.setAdapter(new MyViewpagerAdapter(views));
		setLeftTvOnClick(this);
	}

	
	private void initviews() {
		inflater=LayoutInflater.from(this);
		views=new ArrayList<View>(3);
		view1=inflater.inflate(R.layout.customer_pay_pg1, null);
		views.add(view1);
		initpag1view();
		view2=inflater.inflate(R.layout.customer_pay_pag2, null);
		views.add(view2);
		view3=inflater.inflate(R.layout.customer_pay_pag3, null);
		views.add(view3);
	}

	private void initpag1view() {
		radioGroup=(RadioGroup) view1.findViewById(R.id.cppg1_radiogroup);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_left_tv:
			this.finish();
			break;

		default:
			break;
		}
		
	}

}
