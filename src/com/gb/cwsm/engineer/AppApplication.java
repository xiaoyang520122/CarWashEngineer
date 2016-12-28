package com.gb.cwsm.engineer;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gb.cwsm.engineer.entity.PersistentCookieStore;

public class AppApplication extends Application {
	
	public static boolean MARKER_TYPE=true;
	
	 private static final String TAG = "GetuiSdkDemo";
	 private static DemoHandler handler;

	private static AppApplication mInstance = null;
	public boolean m_bKeyRight = true;
//	public static List<Product> PRODUCTLIST;
	public static String  CID="";
	public static SharedPreferences sp;


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		setMarkerType();
		getCID();
		 Log.i("LONGING", "AppApplication.CID=进入……");
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
		 sp = getSharedPreferences("register_info", Context.MODE_PRIVATE);
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
	        	Log.e("LONGING", "进入AppApplication.CID = " + (String) msg.obj);
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
	                	Log.e("LONGING", "AppApplication.CID = " + (String) msg.obj);
	                	Editor editor = sp.edit();
	                	editor.putString("clientID", (String) msg.obj);
	                	editor.commit();
	                	CID=(String) msg.obj;
	                    break;
	            }
	        }
	    }
}
