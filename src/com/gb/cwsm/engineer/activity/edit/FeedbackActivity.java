package com.gb.cwsm.engineer.activity.edit;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;

public class FeedbackActivity extends BaseActivity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "意见反馈", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.feedback);
		ActivityManagerUtil.getInstance().addToList(this);
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
