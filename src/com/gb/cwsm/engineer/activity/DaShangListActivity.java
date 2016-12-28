package com.gb.cwsm.engineer.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;

public class DaShangListActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "打赏记录", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.dashang_list);
		ActivityManagerUtil.getInstance().addToList(this);
		initview();
	}
	private void initview() {
		setonclick();
	}

	private void setonclick() {
		getleftTv().setOnClickListener(this);
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
