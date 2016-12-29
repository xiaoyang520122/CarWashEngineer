package com.gb.cwsm.engineer.activity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.gb.cwsm.engineer.BaseActivity;
import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.utils.ActivityManagerUtil;
import com.gb.cwsm.engineer.view.MyGridView;

public class EditTimeActivity extends BaseActivity implements OnClickListener {

	private MyGridView gridView;
	private LayoutInflater inflater;
	private SimpleDateFormat fmt;
	private List<boolean[]> weekchecks;
	private long starttime;
	private List<TextView> weeks;
	private int weekitem;
	private int[] ids=new int[] {R.id.dittime_week1,R.id.dittime_week2,R.id.dittime_week3,R.id.dittime_week4,
			R.id.dittime_week5,R.id.dittime_week6,R.id.dittime_week7};
	
	@Override
	protected void onCreate(Bundle paramBundle) {
		settitlename("返回", "设置预约时间", "");
		super.onCreate(paramBundle);
		ActivityManagerUtil.getInstance().addToList(this);
		setContentView(R.layout.edit_book_time);
		initview();
	}

	@SuppressLint("SimpleDateFormat")
	private void initview() {
		setLeftTvOnClick(this);
		fmt=new SimpleDateFormat("HH:mm");
		printdata();
		initweek();
		gridView=(MyGridView) findViewById(R.id.editbook_gridView1);
		
		inflater=LayoutInflater.from(this);
		gridView.setAdapter(new MyGridAdapter());
	}
	
	private void initweek() {
		weeks=new ArrayList<TextView>(7);
		for (int i = 0; i < ids.length; i++) {
			final int point=i;
			TextView tv=(TextView)findViewById(ids[i]);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					weekitem=point;
					gridView.setAdapter(new MyGridAdapter());
				}
			});
			weeks.add(tv);
		}
	}

	public void printdata(){
		try {
			starttime=fmt.parse("06:00").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		weekchecks=new ArrayList<boolean[]>(7);
		for (int i = 0; i < 7; i++) {
			initcheck();
		}
	}
	
	private void initcheck(){
		boolean[] checks=new boolean[34];
		for (boolean b : checks) {
			b=false;
		}
		weekchecks.add(checks);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_left_tv:
			this.finish();
			break;

		default:
			break;
		}
		
	}
	
	public class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return weekchecks.get(weekitem).length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int point, View contentView, ViewGroup arg2) {
			CheckBox box;
			if (contentView==null) {
				box=(CheckBox) inflater.inflate(R.layout.gridview_item, null);
				Date date=new Date(starttime+1800000*point);
				box.setText(fmt.format(date));
				box.setChecked(weekchecks.get(weekitem)[point]);
				box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						 weekchecks.get(weekitem)[point]=arg1;
						 setWeeksBg(weekitem);
					}
				});
			}else {
				return contentView;
			}
			return box;
		}
		private void setWeeksBg(int item) {
			if (ischeckone(item)) {
				weeks.get(item).setBackgroundResource(R.drawable.checkbox_bg_blue);
			}else {
				weeks.get(item).setBackgroundResource(R.drawable.checkbox_bg);
			}
		}
		private boolean ischeckone(int item){
			for (boolean b : weekchecks.get(item)) {
				if (b) {
					return b;
				}
			}return false;
		}
	}
}
