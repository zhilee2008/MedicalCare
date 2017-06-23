package com.ysqm.medicalcare.patient;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;

public class ProjectInfo extends Fragment {
	private String projectId;

	ProjectInfo(String projectId){
		this.projectId = projectId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.activity_project_info, container, false);
		
		Button backButton = (Button) v.findViewById(R.id.backbutton);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// arg0.onKeyDown(KeyEvent.KEYCODE_BACK,null);
				
				new Thread() {
					public void run() {
						try {
							Instrumentation inst = new Instrumentation();
							inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();

			}
		});
		
		LinearLayout tv = (LinearLayout)v.findViewById(R.id.myordbutton);
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(ProjectInfo.this.getActivity().getApplicationContext(), "projectId"+projectId, 5).show();
				FragmentActivity0.changeFragment(new OrderList(projectId));
			}
		});
        LinearLayout tvf = (LinearLayout)v.findViewById(R.id.myfollowbutton);
        tvf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(ProjectInfo.this.getActivity().getApplicationContext(), "projectId"+projectId, 5).show();
				FragmentActivity0.changeFragment(new FollowPlan(projectId));
			}
		});
        return v;
	}
}
