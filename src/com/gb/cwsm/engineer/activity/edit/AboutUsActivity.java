package com.gb.cwsm.engineer.activity.edit;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;

public class AboutUsActivity extends BaseActivity implements OnClickListener {

	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "关于我们", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.about_us);
		intview();
	}

	private void intview() {
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
