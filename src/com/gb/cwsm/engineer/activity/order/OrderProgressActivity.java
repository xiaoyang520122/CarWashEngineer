package com.gb.cwsm.engineer.activity.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.entity.URLs;
import com.gb.cwsm.engineer.utils.CallUtils;
import com.gb.cwsm.engineer.utils.JsonHttpUtils;
import com.gb.cwsm.engineer.utils.LoadingDialog;

public class OrderProgressActivity extends BaseActivity implements OnClickListener {

	private Button startbut;
	private TextView status, nametv, producttv, typetv, viptypetv, datetv, cartv, addresstv;
	private ImageView callimg;
	private List<NameValuePair> pamrams;
	private SimpleDateFormat fmt;
	private LoadingDialog lodialog;

	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "订单信息", "");
		super.onCreate(paramBundle);
		setContentView(R.layout.order_progress);
		EventBus.getDefault().register(this);
		initview();
	}

	@SuppressLint("SimpleDateFormat")
	private void initview() {
		fmt = new SimpleDateFormat("yyyy-MM-dd (E) HH:mm");
		lodialog = new LoadingDialog(this);
		setLeftTvOnClick(this);
		Intent intent = getIntent();
		getNewOrderinfo(intent.getStringExtra("orderSn"));
		startbut = (Button) findViewById(R.id.orprogres_startbut);
		status = (TextView) findViewById(R.id.orprogres_statetv);
		nametv = (TextView) findViewById(R.id.orprogres_membername);
		producttv = (TextView) findViewById(R.id.orprogres_product);
		typetv = (TextView) findViewById(R.id.orprogres_type);
		viptypetv = (TextView) findViewById(R.id.orprogres_viptype);
		datetv = (TextView) findViewById(R.id.orprogres_date);
		cartv = (TextView) findViewById(R.id.orprogres_carinfo);
		addresstv = (TextView) findViewById(R.id.orprogres_address);
		callimg = (ImageView) findViewById(R.id.orprogres_call);

		startbut.setOnClickListener(this);

	}

	private void getNewOrderinfo(String sn) {
		showdialog();
		pamrams = new ArrayList<NameValuePair>(1);
		pamrams.add(new BasicNameValuePair("sn", sn));
		requesthttp(URLs.REQUEST_ORDER_INFO, pamrams, JsonHttpUtils.REQUEST_ORDER_INFO);
	}

	private void requesthttp(final String Url, final List<NameValuePair> pamrams2, final int typecode) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				JsonHttpUtils.doPost(Url, pamrams2, typecode);
			}
		}.start();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void eventNewOrder(NameValuePair value) {
		int code = Integer.valueOf(value.getName());
		switch (code) {
		case JsonHttpUtils.REQUEST_ORDER_INFO:
			isgetordersuccess(value.getValue());
			break;

		default:
			break;
		}
	}

	private void isgetordersuccess(String value) {
		lodialog.dismiss();
		try {
			JSONObject jo1 = new JSONObject(value);
			JSONObject jo2 = jo1.optJSONObject("message");
			if (jo2.optString("type").equals("success")) {
				jiexiorder(jo1.optJSONObject("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void jiexiorder(JSONObject jsdata) {
		JSONObject jsmember = jsdata.optJSONObject("member");
		status.setText(jsdata.optString("orderStatus"));
		nametv.setText(jsmember.optString("name"));
		producttv.setText(jsdata.optString("name"));
		// typetv.setText(jsdata.optString("orderStatus"));
		// viptypetv.setText(jsdata.optString("orderStatus"));
		long mils = Long.valueOf(jsdata.optString("createDate"));
		datetv.setText(fmt.format(new Date(mils)));
		cartv.setText(jsdata.optString("licenceNumber") + " " + jsdata.optString("models"));
		addresstv.setText(jsdata.optString("address"));
		CallUtils.setCallListener(this, jsmember.optString("mobile"), callimg);
	}

	private void showdialog() {
		if (!lodialog.isShowing()) {
			lodialog.setMessage("加载中 稍安勿躁……").show();
		}
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
		status.setText("服务中……");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
