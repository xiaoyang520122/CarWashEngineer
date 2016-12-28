package com.gb.cwsm.engineer.fragment;

import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.activity.DaShangListActivity;
import com.gb.cwsm.engineer.activity.MyPropertyActivity;
import com.gb.cwsm.engineer.activity.OrderListActivity;
import com.gb.cwsm.engineer.activity.edit.EditerCenterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyMenuFragment extends Fragment implements OnClickListener {
	private View mainView;
	private LinearLayout editL;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView=inflater.inflate(R.layout.fragment_my_menu, null);
		initview();
		return mainView;
	}
	private void initview() {
		mainView.findViewById(R.id.menu_edit).setOnClickListener(this);
		mainView.findViewById(R.id.menu_dashang).setOnClickListener(this);
		mainView.findViewById(R.id.menu_orderlist).setOnClickListener(this);
		mainView.findViewById(R.id.my_menu_myproperty).setOnClickListener(this);
		mainView.findViewById(R.id.my_customer_pay).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_edit:
			getActivity().startActivity(new Intent(getActivity(), EditerCenterActivity.class));
			break;
			
		case R.id.menu_dashang:
			getActivity().startActivity(new Intent(getActivity(), DaShangListActivity.class));
			break;
			
		case R.id.menu_orderlist:
			getActivity().startActivity(new Intent(getActivity(), OrderListActivity.class));
			break;
			
		case R.id.my_menu_myproperty:
			getActivity().startActivity(new Intent(getActivity(), MyPropertyActivity.class));
			break;
		case R.id.my_customer_pay:
			getActivity().startActivity(new Intent(getActivity(), MyPropertyActivity.class));
			break;

		default:
			break;
		}
		
	}

}
