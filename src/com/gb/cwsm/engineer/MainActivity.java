package com.gb.cwsm.engineer;

import com.gb.cwsm.engineer.R;
import com.gb.cwsm.engineer.fragment.MyMenuFragment;
import com.gb.cwsm.engineer.fragment.MyMessageFragment;
import com.gb.cwsm.engineer.fragment.SupermainTimeFragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private Fragment mantimefgmt,msgcenfgmt,menufgmt;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private TextView mytv, sptimetv, msgcentertv;
	private Resources res;
	private Drawable drawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initview();
	}

	private void initview() {
		res=getResources();
		this.fm = getSupportFragmentManager();
		mytv = (TextView) findViewById(R.id.main_my);
		sptimetv = (TextView) findViewById(R.id.main_sptime);
		msgcentertv = (TextView) findViewById(R.id.main_msgcenter);

		mytv.setOnClickListener(this);
		sptimetv.setOnClickListener(this);
		msgcentertv.setOnClickListener(this);

		if (mantimefgmt==null) {
			mantimefgmt=new SupermainTimeFragment();
			ft=fm.beginTransaction();
			ft.add(R.id.fragment_id, mantimefgmt);
			ft.show(mantimefgmt);
		}
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		
		ft=fm.beginTransaction();
		switch (v.getId()) {
		case R.id.main_my:
			coverview();
			drawable=res.getDrawable(R.drawable.main_headh);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // ���ñ߽�
			mytv.setCompoundDrawables(null, drawable, null, null);
			mytv.setTextColor(Color.parseColor("#ffffffff"));
			hidefrgmt(ft);
			if (menufgmt==null) {
				menufgmt=new MyMenuFragment();
				ft.add(R.id.fragment_id, menufgmt);
			}
			ft.show(menufgmt);
			break;
			
		case R.id.main_sptime:
			coverview();
			drawable=res.getDrawable(R.drawable.main_table_ico_midle_h);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //���ñ߽�
			sptimetv.setCompoundDrawables(null, drawable, null, null);
			sptimetv.setTextColor(Color.parseColor("#ffffffff"));
			hidefrgmt(ft);
			if (mantimefgmt==null) {
				mantimefgmt=new SupermainTimeFragment();
				ft.add(R.id.fragment_id, mantimefgmt);
			}
			ft.show(mantimefgmt);
			break;
			
		case R.id.main_msgcenter:
			coverview();
			drawable=res.getDrawable(R.drawable.msg_centerh);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //���� �߽�
			msgcentertv.setCompoundDrawables(null, drawable, null, null);
			msgcentertv.setTextColor(Color.parseColor("#ffffffff"));
			hidefrgmt(ft);
			if (msgcenfgmt==null) {
				msgcenfgmt=new MyMessageFragment();
				ft.add(R.id.fragment_id, msgcenfgmt);
			}
			ft.show(msgcenfgmt);
			break;

		default:
			break;
		}
		ft.commit();
	}
	
	private void coverview(){
		drawable=res.getDrawable(R.drawable.main_head);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //��   �ñ߽�
		mytv.setCompoundDrawables(null, drawable, null, null);
		mytv.setTextColor(Color.parseColor("#ffbdbdbd"));
		
		drawable=res.getDrawable(R.drawable.main_table_ico_midle);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // ���ñ߽�
		sptimetv.setCompoundDrawables(null, drawable, null, null);
		sptimetv.setTextColor(Color.parseColor("#ffbdbdbd"));
		
		drawable=res.getDrawable(R.drawable.msg_center);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // ���ñ߽�
		msgcentertv.setCompoundDrawables(null, drawable, null, null);
		msgcentertv.setTextColor(Color.parseColor("#ffbdbdbd"));
	}

	private void  hidefrgmt(FragmentTransaction ft){
		if (msgcenfgmt!=null) {
			ft.hide(msgcenfgmt);
		}
		if (mantimefgmt!=null) {
			ft.hide(mantimefgmt);
		}
		if (menufgmt!=null) {
			ft.hide(menufgmt);
		}
		
	}
}
