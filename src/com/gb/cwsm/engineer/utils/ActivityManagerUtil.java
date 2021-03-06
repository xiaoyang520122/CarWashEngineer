package com.gb.cwsm.engineer.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityManagerUtil {
	
	public static List<Activity> activityList = null;
	private static ActivityManagerUtil amu = null;

	
	public static ActivityManagerUtil getInstance() {
		if (amu == null) {
			amu = new ActivityManagerUtil();
		}
		if (activityList == null) {
			activityList = new ArrayList();
		}
		return amu;
	}

	
	public void addToList(Activity paramActivity) {
		if (amu != null) {
			activityList.add(paramActivity);
		}
	}

	
	@SuppressLint("NewApi")
	public void finishAllActivity() {
//		Iterator localIterator;
//		if (amu != null) {
//			localIterator = activityList.iterator();
//			if (!localIterator.hasNext()) {
//				return;
//			}
//			Activity localActivity = (Activity) localIterator.next();
//			if (localActivity != null) {
//				localActivity.finish();
//			}
//		}
		for (Activity activity:activityList) {
//			if (!activity.isDestroyed()) {
				activity.finish();
//			}
		}
	}
}
