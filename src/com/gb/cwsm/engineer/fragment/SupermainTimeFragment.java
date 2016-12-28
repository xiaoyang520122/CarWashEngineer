package com.gb.cwsm.engineer.fragment;


import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.activity.MapActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SupermainTimeFragment extends Fragment implements OnClickListener {
	
	private View view;
	private TextView hotmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=LayoutInflater.from(getActivity()).inflate(R.layout.superman_time_fragment, null);
		initview();
		return view;
	}

	private void initview() {
		hotmap=(TextView) view.findViewById(R.id.suptime_hotmap);
		
		hotmap.setOnClickListener(this);
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

}
