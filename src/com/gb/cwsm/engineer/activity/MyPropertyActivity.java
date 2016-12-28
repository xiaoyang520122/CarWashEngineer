package com.gb.cwsm.engineer.activity;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class MyPropertyActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		settitlename("返回", "我的资产", "···");
		setContentView(R.layout.my_property);
		initview();
	}

	private void initview() {
		setLeftTvOnClick(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_left_tv:
			finish();
			break;

		default:
			break;
		}
	}
}
