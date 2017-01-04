package com.gb.cwsm.engineer.fragment;

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

import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.activity.MapActivity;
import com.gb.cwsm.engineer.activity.order.OrderProgressActivity;
import com.gb.cwsm.engineer.entity.URLs;
import com.gb.cwsm.engineer.utils.JsonHttpUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SupermainTimeFragment extends Fragment implements OnClickListener {

	private View view;
	private TextView hotmap,nomsgtv;
	private ListView orderlistview;
	private LayoutInflater inflater;
	private List<String> orders=new ArrayList<String>(1);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_superman_time, null);
		EventBus.getDefault().register(this);
		initview();
		return view;
	}

	private void initview() {
		inflater = getActivity().getLayoutInflater();
		view.findViewById(R.id.suptime_hotmap).setOnClickListener(this);
		nomsgtv= (TextView) view.findViewById(R.id.ordermain_noimg);
		orderlistview = (ListView) view.findViewById(R.id.ordermain_listview);
		setadapter();
		setonitemonclick();
	}

	private void setadapter() {
		if (orders.size()<=0) {
			nomsgtv.setVisibility(View.VISIBLE);
		}else {
			nomsgtv.setVisibility(View.GONE);
			orderlistview.setAdapter(new NewOrderAdapter());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.suptime_hotmap:
			getActivity().startActivity(new Intent(getActivity(), MapActivity.class));
			break;

		default:
			break;
		}
	}

	private void setonitemonclick() {
		orderlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent=new Intent(SupermainTimeFragment.this.getActivity(),OrderProgressActivity.class);
				intent.putExtra("orderSn", orders.get(arg2).split("#")[0]);
				SupermainTimeFragment.this.getActivity().startActivity(intent);
			}
		});
	}
	
	@Subscribe(threadMode=ThreadMode.MAIN)
	public void eventNewOrder(NameValuePair value){
		int code=Integer.valueOf(value.getName());
		switch (code) {
		case JsonHttpUtils.NEW_ORDER_PAYLOAD:
			 Log.d("GTIntentService", "主页接收到透传消息 = " + value.getValue());
			orders.add(value.getValue());
			setadapter();
			break;

		default:
			break;
		}
	}

	private class NewOrderAdapter extends BaseAdapter {
		
		SimpleDateFormat smp=new SimpleDateFormat("yyyy-MM-dd HH:mm");

		@Override
		public int getCount() {
			return orders.size();
		}

		@Override
		public Object getItem(int arg0) {
			return orders.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int point, View conview, ViewGroup arg2) {
			ViewHoder hoder = null;
			if (conview==null) {
				hoder=new ViewHoder();
				conview = inflater.inflate(R.layout.new_order_item, null);
				hoder.date=(TextView) conview.findViewById(R.id.neworitem_date);
				hoder.sn=(TextView) conview.findViewById(R.id.neworitem_sn);
				hoder.type=(TextView) conview.findViewById(R.id.neworitem_type);
				conview.setTag(hoder);
			}else {
				hoder=(ViewHoder) conview.getTag();
			}
			try {
				String []datainfo = orders.get(point).split("#");
				try {
					long mils=Long.valueOf(datainfo[1]);
					hoder.date.setText(smp.format(new Date(mils)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				hoder.sn.setText(datainfo[0]);
				hoder.type.setText(datainfo[2]);
				
//				long mils=jo.optLong("createDate", 946656000000L);
//				hoder.date.setText(smp.format(new Date(mils)));
//				hoder.sn.setText(jo.optString("sn"));
//				hoder.type.setText(jo.optString("type"));
			} catch (Exception e) { e.printStackTrace(); }
			
			return conview;
		}
		
		private class ViewHoder{
			public TextView date,type,sn;
		}

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
