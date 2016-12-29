package com.gb.cwsm.engineer.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;

public class ConplaintActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "投诉扣款", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.complaint);
		initveiw();
	}

	private void initveiw() {
		setLeftTvOnClick(this);
		PopupWindow popuw=setpopupwindow(this, R.layout.more_menu);
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
