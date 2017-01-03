package com.gb.cwsm.engineer.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.MainActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.entity.URLs;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;
import com.gb.cwsm.engineer.utils.JsonHttpUtils;
import com.gb.cwsm.engineer.utils.LoadingDialog;
import com.gb.cwsm.engineer.utils.RegexUtil;
import com.gb.cwsm.engineer.utils.ToastUtil;

public class LogingActivity extends BaseActivity implements OnClickListener {

	private Button logbutton;
	private TextView newnsetv, getcodeTv;
	private EditText mobileedit,logingcodeedit;
	private List<NameValuePair> params;
	private String phoneNumber;
	private LoadingDialog loadDlog;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loging);
		ActivityManagerUtil.getInstance().addToList(this);
		isloging();
		EventBus.getDefault().register(this);
		initview();
	}

	private void isloging() {
		sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		phoneNumber=sp.getString("phonenumber", "");
		Log.i("LONGING", "phoneNumber=="+phoneNumber);
		if (!TextUtils.isEmpty(phoneNumber)) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		codetextToDefault();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		phoneNumber=sp.getString("phonenumber", "");
		if (TextUtils.isEmpty(phoneNumber)) {
			ActivityManagerUtil.getInstance().finishAllActivity();
		}
	}

	private void initview() {
		logbutton = (Button) findViewById(R.id.Loging_button);
		newnsetv = (TextView) findViewById(R.id.loging_register_NewUser);
		getcodeTv = (TextView) findViewById(R.id.loging_qingqiucode);
		mobileedit = (EditText) findViewById(R.id.loging_phonenumber);
		logingcodeedit = (EditText) findViewById(R.id.loging_yanzheng);

		logbutton.setOnClickListener(this);
		getcodeTv.setOnClickListener(this);
		newnsetv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.Loging_button:
			sendlogingcode();
			break; 
			
		case R.id.loging_register_NewUser:
			startActivity(new Intent(this, RegisterActivity.class));
			this.finish();
			break;

		case R.id.loging_qingqiucode:
			yanzhengMobile();
			break;

		default:
			break;
		}

	}

	private void sendlogingcode() {
		if (TextUtils.isEmpty(logingcodeedit.getText().toString())) {
			logingcodeedit.setError("验证码不能为空！");
			logingcodeedit.setFocusable(true);
			logingcodeedit.requestFocus();
			return;
		}
	/**测试登陆验证**/
		if (logingcodeedit.getText().toString().equals("000000")) {
			sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
			Editor editor=sp.edit();
			editor.putString("username", "测试用户不能联网操作");
			editor.putString("phonenumber", "000000");
			editor.commit();
			editor.clear();
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return;
		}
	/***测试登陆验证结束***/
		
		if (TextUtils.isEmpty(phoneNumber)) {
			phoneNumber=mobileedit.getText().toString();
		}
		showdialog("稍等……");
		params=new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("mobile", phoneNumber));
		params.add(new BasicNameValuePair("captcha", logingcodeedit.getText().toString()));
		new Thread(){
			@Override
			public void run() {
				super.run();
				JsonHttpUtils.doPost(URLs.POST_DX_LOGING, params, JsonHttpUtils.LOGING_BY_DX, LogingActivity.this);
			}
		}.start();
	}

	private void yanzhengMobile() {
		if(!sendflag) return;
		phoneNumber = mobileedit.getText().toString();
		if (!RegexUtil.isMobileNO(phoneNumber)) {
			mobileedit.setError("请输入正确的手机号");
			mobileedit.setFocusable(true);
			mobileedit.requestFocus();
			return;
		}
		sendflag=false;
		Setprivatetime();
		new Thread() {
			@Override
			public void run() {
				super.run();
				params = new ArrayList<NameValuePair>(1);
				params.add(new BasicNameValuePair("mobile", phoneNumber));
				params.add(new BasicNameValuePair("operatorType", "engineer"));
				JsonHttpUtils.doPost(URLs.GET_CHECK_MOBILE, params, JsonHttpUtils.GET_CHECK_MOBILE, LogingActivity.this);
			}
		}.start();

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void eventloging(NameValuePair value) {
		int code = Integer.valueOf(value.getName());
		switch (code) {
		case JsonHttpUtils.GET_CHECK_MOBILE:
			Log.i("LONGING", "登陆  检查手机号=="+value.getValue());
			checkmobile(value.getValue());
			break;
		case JsonHttpUtils.GET_DXYZ_CODE:
			Log.i("LONGING", "登陆  获取验证短信=="+value.getValue());
			getlogingcode(value.getValue());
			break;
			
		case JsonHttpUtils.LOGING_BY_DX:
			Log.i("LONGING", "登陆  短信登陆=="+value.getValue());
			islogingsuccess(value.getValue());
			break;
			
		case 8888:
			settime();
			break;

		default:
			break;
		}
	}
	
	private void settime() {
		if (time > 60) {
			codetextToDefault();
		} else  if (!sendflag) {
			getcodeTv.setBackgroundResource(R.drawable.corners_hui_4_5dp);
			getcodeTv.setText((60 - time) + "秒后重发");
		}
	}

	private void islogingsuccess(String jsonvalue) {
		Log.i("LONGING", "短信登陆验证返回数据="+jsonvalue);
		loadDlog.dismiss();
		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				saveusermsg(jo1.getJSONObject("data"));
			}else {
				logingcodeedit.setError(jo2.getString("content"));
				logingcodeedit.setFocusable(true);
				logingcodeedit.requestFocus();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("CommitPrefEdits")
	private void saveusermsg(JSONObject jsonObject) throws JSONException {
		sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putString("username", jsonObject.getString("name"));
		editor.putString("phonenumber", phoneNumber);
		editor.commit();
		editor.clear();
		startActivity(new Intent(this, MainActivity.class));
		codetextToDefault();
		finish();
	}

	private void getlogingcode(String jsonvalue) {
		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				ToastUtil.showToastShort(this, "发送成功！");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void checkmobile(String jsonvalue) {

		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				showregistermsg();
			} else if (jo2.getString("type").equals("warn")) {
				getLogingCode();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showregistermsg() {
		new AlertDialog.Builder(this).setTitle("提示！").setMessage(getString(R.string.no_register_msg)).setPositiveButton("马上注册", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent=new Intent(LogingActivity.this, RegisterActivity.class);
				intent.putExtra("phonenumber", phoneNumber);
				LogingActivity.this.startActivity(intent);
				finish();
			}
		}).setNegativeButton("重输", null).create().show();
	}

	private void getLogingCode() {

		new Thread() {
			@Override
			public void run() {
				super.run();
				params = new ArrayList<NameValuePair>(2);
				params.add(new BasicNameValuePair("mobile", phoneNumber));
				params.add(new BasicNameValuePair("type", "memberLogin"));
				JsonHttpUtils.doPost(URLs.GET_DXYZ_CODE, params, JsonHttpUtils.GET_DXYZ_CODE, LogingActivity.this);
			}
		}.start();
	}

	private void showdialog(String msg) {
		if (loadDlog == null) {
			loadDlog = new LoadingDialog(this);
		}
		if (loadDlog.isShowing()) {
			return;
		}
		loadDlog.setMessage(msg).show();
	}
	
	private void codetextToDefault() {
		if (timer!=null) {
			timer.cancel();
		}
		getcodeTv.setBackgroundResource(R.drawable.corners_blue_button5);
		getcodeTv.setText("获取验证码");
		time=1;
		sendflag=true;
	}
	
	private int time=1;
	private boolean sendflag=true;
	private  Timer timer;
	
	private void Setprivatetime(){
		
		TimerTask task = new TimerTask(){    
		     public void run(){   
		    	 NameValuePair params=new BasicNameValuePair("8888", (60-time)+"");
	    		 EventBus.getDefault().post(params);
		    	 time++;
		     }    
		 };    
		timer = new Timer();  
		timer.schedule(task, 1,1000);
	}

}
