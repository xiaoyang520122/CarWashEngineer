package com.gb.cwsm.engineer.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.MainActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;

public class SupplementActivity extends BaseActivity implements OnClickListener {

	private Button submitbt;
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(paramBundle);
		setContentView(R.layout.supplement_info);
		ActivityManagerUtil.getInstance().addToList(this);
		initview();
	}

	private void initview() {
		submitbt=(Button) findViewById(R.id.supp_submit);
		
		submitbt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.supp_submit:
			startActivity(new Intent(this, MainActivity.class));
			break;

		default:
			break;
		}
		
	}
}
