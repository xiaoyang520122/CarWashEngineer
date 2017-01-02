package com.gb.cwsm.engineer.activity.order;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;

public class OrderProgressActivity extends BaseActivity implements OnClickListener {
	
	private Button startbut;
	private TextView states;
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "未完成订单信息", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.order_progress);
		initview();
	}

	private void initview() {
		setLeftTvOnClick(this);
		startbut=(Button) findViewById(R.id.orprogres_startbut);
		states=(TextView) findViewById(R.id.orprogres_statetv);
		
		startbut.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_left_tv:
			this.finish();
			break;
		case R.id.orprogres_startbut:
			startserve();
			break;

		default:
			break;
		}
		
	}

	private void startserve() {
		startbut.setText("服务完成");
		states.setText("服务中……");
	}

}
