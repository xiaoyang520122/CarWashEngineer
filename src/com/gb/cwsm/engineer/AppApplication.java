package com.gb.cwsm.engineer;

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
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.gb.cwsm.engineer.entity.PersistentCookieStore;
import com.gb.cwsm.engineer.entity.URLs;
import com.gb.cwsm.engineer.entity.User;
import com.gb.cwsm.engineer.utils.JsonHttpUtils;
import com.gb.cwsm.engineer.utils.Md5Util;

public class AppApplication extends Application {
	
	public static boolean MARKER_TYPE=true;
	private List<NameValuePair> NVparames;
	 private static final String TAG = "GetuiSdkDemo";
	 private static DemoHandler handler;

	private static AppApplication mInstance = null;
	public boolean m_bKeyRight = true;
//	public static List<Product> PRODUCTLIST;
	public static String  CID="";
	public static User USER;
	public static SharedPreferences sp;


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		sp = getSharedPreferences("carwashsuperman", Context.MODE_PRIVATE);
		SDKInitializer.initialize(this);
		getCID();
		Loginguser();
		EventBus.getDefault().register(this);
		setMarkerType();
		
	}

	private void getCID() {
		if (handler == null) {
            handler = new DemoHandler();
        }
		CID=sp.getString("clientID", "nomsg");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		// 整体摧毁的时候调用这个方法
	}

	public static AppApplication getInstance() {
		return mInstance;
	}

	private void setMarkerType(){
		boolean type = sp.getBoolean("markertype", true);
			MARKER_TYPE=type;
	}

	public PersistentCookieStore getPersistentCookieStore() {
		PersistentCookieStore cookieStore=new PersistentCookieStore(mInstance);
		return cookieStore;
	}
	
	public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }
	
	 public static class DemoHandler extends Handler {

	        @Override
	        public void handleMessage(Message msg) {
//	        	Log.e("LONGING", "进入AppApplication.CID = " + (String) msg.obj);
	            switch (msg.what) {
//	                case 0:
//	                    if (demoActivity != null) {
//	                        payloadData.append((String) msg.obj);
//	                        payloadData.append("\n");
//	                        if (GetuiSdkDemoActivity.tLogView != null) {
//	                            GetuiSdkDemoActivity.tLogView.append(msg.obj + "\n");
//	                        }
//	                    }
//	                    break;

	                case 1:
//	                    if (demoActivity != null) {
//	                        if (GetuiSdkDemoActivity.tLogView != null) {
//	                            GetuiSdkDemoActivity.tView.setText((String) msg.obj);
//	                        }
//	                    }
//	                	Log.e("LONGING", "AppApplication.CID = " + (String) msg.obj);
	                	Editor editor = sp.edit();
	                	editor.putString("clientID", (String) msg.obj);
	                	editor.commit();
	                	CID=(String) msg.obj;
	                    break;
	            }
	        }
	    }
	 private void Loginguser() {
			String mobile=sp.getString("phonenumber", "");
			if (TextUtils.isEmpty(mobile)) {
				return;
			}
			NVparames = new ArrayList<NameValuePair>(2);
			NVparames.add(new BasicNameValuePair("username", mobile));
			NVparames.add(new BasicNameValuePair("enPassword", Md5Util.MD5("123456")));
			NVparames.add(new BasicNameValuePair("cid",AppApplication.CID));
			new Thread() {
				@Override
				public void run() {
					super.run();
					JsonHttpUtils.doPost(URLs.LOGING_BY_PASS, NVparames, 555, mInstance);
				}
			}.start();
		}
		
		@Subscribe(threadMode=ThreadMode.MAIN)
		public void eventloging(NameValuePair value){
			int code = Integer.valueOf(value.getName());
			switch (code) {
			case 555:
				islogingsuccess(value.getValue());
				break;

			default:
				break;
			}
		}

		private void islogingsuccess(String jsonstr) {
			try {
				JSONObject jo1=new JSONObject(jsonstr);
				JSONObject jo2=jo1.getJSONObject("message");
				JSONObject jo3=jo1.getJSONObject("data");
				if (jo2.getString("type").equals("success")) {
					getusermsg(jo3);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@SuppressLint("SimpleDateFormat")
		public static  void getusermsg(JSONObject jo3) {
			USER=new User();
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy年MM月dd日");
			try {
				long mi=Long.valueOf(jo3.getString("birth"));
				String birthstr=fmt.format(new Date(mi));
				USER.setId(jo3.getString("id"));
				USER.setBirth(birthstr);
				USER.setEmail(jo3.getString("email"));
				USER.setGender(jo3.getString("gender"));
				USER.setMobile(jo3.getString("mobile"));
				USER.setName(jo3.getString("name"));
				JSONObject jo4=jo3.getJSONObject("memberRank");
				USER.setVip(jo4.getString("name"));
//				USER.setAreaId(jo3.getString("id"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
}
