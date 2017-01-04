package com.gb.cwsm.engineer;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gb.cwsm.engineer.getui.DemoIntentService;
import com.gb.cwsm.engineer.getui.DemoPushService;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;
import com.gb.cwsm.engineer.utils.LoadingDialog;
import com.igexin.sdk.PushManager;

/**
 * 基础Activity类 BaseActivity.java Created on: 2014-11-19 Author: Rex Yu
 * rexyu@baoxiansoft.com
 */
@SuppressLint("NewApi")
public class BaseActivity extends FragmentActivity {
	public static String SHARED_PREFERENCE_FILE;
	public static String SHARED_PREFERENCE_KEY_GESTURE_NEEDED;
	public static String SHARED_PREFERENCE_KEY_GESTURE_PASSWORD;
	public static String SHARED_PREFERENCE_KEY_GUIDED;
	public static String SHARED_PREFERENCE_KEY_IS_REMEMBER_PWD_CHECKED;
	public static String SHARED_PREFERENCE_KEY_NEW_GUANGGAO;
	public static String SHARED_PREFERENCE_KEY_USER_NAME;
	public static String SHARED_PREFERENCE_KEY_USER_PWD;
	public static String SHARED_PREFERENCE_KEY_WARN_ACCOUNT;
	public static String SHARED_PREFERENCE_KEY_WARN_HOME;
	public static String SHARED_PREFERENCE_KEY_WARN_INVEST;
	public static String SHARED_PREFERENCE_KEY_WARN_MORE_NEW_BLOCK;
	public static String SHARED_PREFERENCE_KEY_WARN_ORDER_DETAIL;
	public static String SHARED_PREFRENCES_KEY_NEW_MOREGONGGAO;
	public static int heightPixel;
	public static boolean isLoginActivityShowing = false;
	public static AlertDialog warnDialog;
	public static LoadingDialog lodingDialog; 
	public static int widthPixel = 0;
	public static double widthScale;
	private PopupWindow popupWindow;

	private ActionBar actionBar;
	private View mActionBarView;
	private TextView tvL, tvTitle, tvR;
	private ImageView  more;

	static {
		heightPixel = 0;
		widthScale = 0.0D;
	}

	private static AlertDialog createDialog(Context paramContext, String paramString) {
		warnDialog = new AlertDialog.Builder(paramContext).setTitle("提示消息").setMessage(paramString).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
		return warnDialog;
	}

	public static LoadingDialog getLoadingDialog(Activity paramActivity, String paramString1) {
		lodingDialog =new LoadingDialog(paramActivity);
		lodingDialog.setMessage(paramString1);
		return lodingDialog;
	}

	public static SharedPreferences getSharedPreference(Context paramContext) {
		return paramContext.getSharedPreferences(SHARED_PREFERENCE_FILE, 4);
	}

	private void init() {
		widthScale = widthPixel / 480.0D;
		System.out.println(widthPixel + ":   :" + heightPixel);
	}

	public static void requestActivity(Activity paramActivity) {
		// paramActivity.requestWindowFeature(1);//不显示ActionBar
		// paramActivity.setRequestedOrientation(1);
		ActivityManagerUtil.getInstance().addToList(paramActivity);
	}


	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		// com.getui.demo.DemoPushService 为第三方自定义推送服务
		PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
		// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
		PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
	}

	public void CreatActionBar() {
		actionBar = this.getActionBar();
		ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
		actionBar.setCustomView(mActionBarView, lp);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		initavtionview();
		requestActivity(this);
		init();
	}

	protected void onPause() {
		super.onPause();
	}

	private void initavtionview() {
		tvL = (TextView) mActionBarView.findViewById(R.id.action_left_tv);
		tvTitle = (TextView) mActionBarView.findViewById(R.id.action_title_tv);
		tvR = (TextView) mActionBarView.findViewById(R.id.action_right_tv);
//		logo = (ImageView) findViewById(R.id.center_logo_img);
		more = (ImageView) findViewById(R.id.more_img);
	}

	public void settitlename(String titleLeft, String titleName, String titleRight) {
		CreatActionBar();
		if (!titleLeft.equals("") && titleLeft != null) {
			tvL.setText(titleLeft);
		} else {
			tvL.setVisibility(View.INVISIBLE);
		}

		if (!titleName.equals("") && titleName != null) {
			tvTitle.setText(titleName);
//			logo.setVisibility(View.GONE);
		}

		if (!titleRight.equals("") && titleRight != null) {
			tvR.setText(titleRight);
			more.setVisibility(View.GONE);
		}

	}

	/**
	 * 设置标题栏左侧文本单击事件
	 * 
	 * @param clickListener
	 */
	public void setLeftTvOnClick(OnClickListener clickListener) {
		tvL.setOnClickListener(clickListener);
	}
	
	public TextView getleftTv(){
		return tvL;
	}

	/**
	 * 设置标题栏右侧文本单击事件
	 * 
	 * @param clickListener
	 */
	public void setRightTvOnClick(OnClickListener clickListener) {
		tvR.setOnClickListener(clickListener);
	}




	/**
	 * 显示中间logo图标
	 */
	public void SetLogo() {
//		logo.setVisibility(View.VISIBLE);
		tvTitle.setVisibility(View.GONE);
	}

	/**
	 * 设置标题栏右侧图像
	 */
	public void SetMoreimg(int resId) {
		more.setImageResource(resId);
	}

	/**
	 * 设置标题栏右侧图像单击事件
	 */
	public void SetMoreOnclick(OnClickListener onClickListener) {
		more.setOnClickListener(onClickListener);
	}
	
	public PopupWindow setpopupwindow(Context context,int layout){
		View view=((Activity)context).getLayoutInflater().inflate(layout, null);
		popupWindow=new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap)null));
		more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popupWindow.showAsDropDown(more);
			}
		});
		return popupWindow;
	}

}
