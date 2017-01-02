package com.gb.cwsm.engineer.fragment;

import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.activity.MapActivity;
import com.gb.cwsm.engineer.activity.order.OrderProgressActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private TextView hotmap;
	private ListView orderlistview;
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_superman_time, null);
		initview();
		return view;
	}

	private void initview() {
		inflater = getActivity().getLayoutInflater();
		view.findViewById(R.id.suptime_hotmap).setOnClickListener(this);
		orderlistview = (ListView) view.findViewById(R.id.ordermain_listview);
		orderlistview.setAdapter(new NewOrderAdapter());
		setonitemonclick();
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
				SupermainTimeFragment.this.getActivity()
				.startActivity(new Intent(SupermainTimeFragment.this.getActivity(),OrderProgressActivity.class));
			}
		});
	}

	private class NewOrderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 1;
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
		public View getView(int arg0, View conview, ViewGroup arg2) {
			conview = inflater.inflate(R.layout.new_order_item, null);
			return conview;
		}

	}
}
