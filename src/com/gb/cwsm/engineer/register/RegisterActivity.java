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
import com.gb.cwsm.engineer.utils.JsonHttpUtils;
import com.gb.cwsm.engineer.utils.LoadingDialog;
import com.gb.cwsm.engineer.utils.RegexUtil;
import com.gb.cwsm.engineer.utils.ToastUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private Button submitbt;
	private TextView getcodeTv;
	private EditText phoneedit,codeedit,usernameedit,emailedit;
	private List<NameValuePair> params;
	private String phoneNumber;
	private LoadingDialog loadDlog;
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(paramBundle);
		setContentView(R.layout.register_activity);
		EventBus.getDefault().register(this);
		initview();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void initview() {
		submitbt=(Button) findViewById(R.id.register_submit);
		phoneedit=(EditText) findViewById(R.id.register_phone);
		codeedit=(EditText) findViewById(R.id.register_code);
		usernameedit=(EditText) findViewById(R.id.register_username);
		emailedit=(EditText) findViewById(R.id.register_email);
		getcodeTv=(TextView) findViewById(R.id.register_qingqiucode);
		
		submitbt.setOnClickListener(this);
		getcodeTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_submit:
			sendlogingcode();
			break;
			
		case R.id.register_qingqiucode:
			yanzhengMobile();
			break;

		default:
			break;
		}
	}

	private void sendlogingcode() {
		
		if (TextUtils.isEmpty(codeedit.getText().toString())) {
			codeedit.setError("验证码不能为空！");
			codeedit.setFocusable(true);
			codeedit.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(usernameedit.getText().toString())) {
			usernameedit.setError("用户名不能为空！");
			usernameedit.setFocusable(true);
			usernameedit.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(emailedit.getText().toString())) {
			emailedit.setError("邮箱不能为空！");
			emailedit.setFocusable(true);
			emailedit.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(phoneNumber)) {
			phoneNumber=phoneedit.getText().toString();
		}
		showdialog("稍等……");
		params=new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("mobile", phoneNumber));
		params.add(new BasicNameValuePair("operatorType", "engineer"));
		params.add(new BasicNameValuePair("name", usernameedit.getText().toString()));
		params.add(new BasicNameValuePair("captcha", codeedit.getText().toString()));
		params.add(new BasicNameValuePair("password", "123456"));
		new Thread(){
			@Override
			public void run() {
				super.run();
				JsonHttpUtils.doPost(URLs.POST_DX_REGISTER, params, JsonHttpUtils.REGISTER_BY_DX, RegisterActivity.this);
			}
		}.start();
	}

	private void yanzhengMobile() {
		if (!sendflag) return;
		phoneNumber = phoneedit.getText().toString();
		if (!RegexUtil.isMobileNO(phoneNumber)) {
			phoneedit.setError("请输入正确的手机号");
			phoneedit.setFocusable(true);
			phoneedit.requestFocus();
			return;
		}
		sendflag = false;
		Setprivatetime();
		new Thread() {
			@Override
			public void run() {
				super.run();
				params = new ArrayList<NameValuePair>(1);
				params.add(new BasicNameValuePair("mobile", phoneNumber));
				params.add(new BasicNameValuePair("operatorType", "engineer"));
				JsonHttpUtils.doPost(URLs.GET_CHECK_MOBILE, params, JsonHttpUtils.R_GET_CHECK_MOBILE, RegisterActivity.this);
			}
		}.start();

	}
	
	@Subscribe(threadMode=ThreadMode.MAIN)
	public void eventregister(NameValuePair value){
		int code=Integer.valueOf(value.getName());
		switch (code) {
		case JsonHttpUtils.R_GET_CHECK_MOBILE:
			Log.i("LONGING", "检查手机号=="+value.getValue());
			isregisted(value.getValue());
			break;
			
		case JsonHttpUtils.GET_DXYZ_CODE:
			Log.i("LONGING", "获取验证码=="+value.getValue());
			iscodesend(value.getValue());
			break;
			
		case JsonHttpUtils.REGISTER_BY_DX:
			Log.i("LONGING", "短信登陆=="+value.getValue());
			isRegistersuccess(value.getValue());
			break;
			
		case JsonHttpUtils.MODIFY_USER_MSG:
			Log.i("LONGING", "修改个人信息=="+value.getValue());
//			isModifySuccess(value.getValue());
			break;
			
		case 8888:
			settime();
			break;

		default:
			break;
		}
		
	}
	
//	private void isModifySuccess(String jsonvalue) {
//		Log.i("LONGING", "短信登陆验证返回数据="+jsonvalue);
//		loadDlog.dismiss();
//		try {
//			JSONObject jo1 = new JSONObject(jsonvalue);
//			JSONObject jo2 = jo1.getJSONObject("message");
//			if (jo2.getString("type").equals("success")) {
//				saveusermsg2(jo1.getJSONObject("data"));
//			}else {
//				
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//	}

	private void isRegistersuccess(String jsonvalue) {
		Log.i("LONGING", "短信登陆验证返回数据="+jsonvalue);
//		loadDlog.dismiss();
		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				saveusermsg();
			}else {
				codeedit.setError(jo2.getString("content"));
				codeedit.setFocusable(true);
				codeedit.requestFocus();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressLint("CommitPrefEdits")
	private void saveusermsg() {
		SharedPreferences sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putString("username", usernameedit.getText().toString());
		editor.putString("phonenumber", phoneNumber);
		editor.commit();
		editor.clear();
		timer.cancel();
		getcodeTv.setBackgroundResource(R.drawable.corners_blue_button5);
		getcodeTv.setText("获取验证码");
		startActivity(new Intent(this, SupplementActivity.class));
		finish();
//		modifyusermsg();
	}
//	@SuppressLint("CommitPrefEdits")
//	private void saveusermsg2(JSONObject jsonObject) throws JSONException {
//		SharedPreferences sp=getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
//		Editor editor=sp.edit();
//		editor.putString("username", jsonObject.getString("name"));
//		editor.putString("phonenumber", phoneNumber);
//		editor.commit();
//		editor.clear();
//		timer.cancel();
//		getcodeTv.setBackgroundResource(R.drawable.corners_blue_button5);
//		getcodeTv.setText("获取验证码");
//		startActivity(new Intent(this, SupplementActivity.class));
//		finish();
//	}

//	private void modifyusermsg() {
//		params=new ArrayList<NameValuePair>(2);
//		params.add(new BasicNameValuePair("name", usernameedit.getText().toString()));
//		params.add(new BasicNameValuePair("email", emailedit.getText().toString()));
//		
//		new Thread(){
//			@Override
//			public void run() {
//				super.run();
//				JsonHttpUtils.doPost(URLs.MODIFY_USER_MSG, params, JsonHttpUtils.MODIFY_USER_MSG, RegisterActivity.this);
//			}
//		}.start();
//	}

	private void iscodesend(String jsonvalue) {
		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				ToastUtil.showToastShort(this, "发送成功！");
			}else {
				ToastUtil.showToastShort(this, "发送失败！");
			}
		} catch (JSONException e) {
			ToastUtil.showToastShort(this, "发送失败！");
			e.printStackTrace();
		}
	}
	
	private void isregisted(String jsonvalue) {
		try {
			JSONObject jo1 = new JSONObject(jsonvalue);
			JSONObject jo2 = jo1.getJSONObject("message");
			if (jo2.getString("type").equals("success")) {
				getRegisterCode();
			} else if (jo2.getString("type").equals("warn")) {
				showregistermsg(jo2.getString(""));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void getRegisterCode() {

		new Thread() {
			@Override
			public void run() {
				super.run();
				params = new ArrayList<NameValuePair>(2);
				params.add(new BasicNameValuePair("mobile", phoneNumber));
				params.add(new BasicNameValuePair("type", "memberRegister"));
				JsonHttpUtils.doPost(URLs.GET_DXYZ_CODE, params, JsonHttpUtils.GET_DXYZ_CODE, RegisterActivity.this);
			}
		}.start();
	}
	
	private void showregistermsg(String msg) {
		new AlertDialog.Builder(this).setTitle("提示！").setMessage(msg).setPositiveButton("返回登录", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LogingActivity.class));
				finish();
			}
		}).setNegativeButton("重输", null).create().show();
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
	
	private void settime() {
		if (time > 60) {
			getcodeTv.setBackgroundResource(R.drawable.corners_blue_button5);
			getcodeTv.setText("获取验证码");
			sendflag = true;
			time = 1;
			timer.cancel();
		} else {
			getcodeTv.setBackgroundResource(R.drawable.corners_hui_4_5dp);
			getcodeTv.setText((60 - time) + "秒后重发");
		}
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
