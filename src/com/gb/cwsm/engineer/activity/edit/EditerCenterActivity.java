package com.gb.cwsm.engineer.activity.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.register.LogingActivity;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;

public class EditerCenterActivity extends BaseActivity implements OnClickListener {

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "设置中心", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.edit_center);
		ActivityManagerUtil.getInstance().addToList(this);
		initview();
	}

	private void initview() {
		sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		setLeftTvOnClick(this);
		findViewById(R.id.editc_exeit_user).setOnClickListener(this);
		findViewById(R.id.editc_exeit_app).setOnClickListener(this);
		findViewById(R.id.editc_Feedback).setOnClickListener(this);
		findViewById(R.id.editc_about_us).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editc_exeit_user:
			exeituser();
			break;
		case R.id.editc_exeit_app:
			ActivityManagerUtil.getInstance().finishAllActivity();
			break;
		case R.id.editc_Feedback:
			startActivity(new Intent(this, FeedbackActivity.class));
			break;
		case R.id.editc_about_us:
			startActivity(new Intent(this, AboutUsActivity.class));
			break;
		case R.id.action_left_tv:
			finish();
			break;
		default:
			break;
		}
	}

	private void exeituser() {
		Editor editor=sp.edit();
		editor.putString("username", "");
		editor.putString("phonenumber", "");
		editor.commit();
		editor.clear();
		startActivity(new Intent(this, LogingActivity.class));
	}
}
