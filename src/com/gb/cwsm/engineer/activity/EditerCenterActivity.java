package com.gb.cwsm.engineer.activity;

import android.os.Bundle;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;

public class EditerCenterActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "设置中心", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.edit_center);
	}
}
