package com.gb.cwsm.engineer.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

public class CallUtils {
	
	
	public static void setCallListener(final Context context,final String phone,View v){
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CallUtils.call(context,phone);
			}
		});
	}
	
	public static void call(Context context,String phone){
		Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
		context.startActivity(intent);  
	}

}
