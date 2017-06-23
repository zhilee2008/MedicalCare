package com.ysqm.medicalcare.crc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.FragmentActivity4;
import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.MainTabLayout;
import com.ysqm.medicalcare.R;

public class CRC_Setting extends Fragment {
    //	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        View v = inflater.inflate(R.layout.crc_activity_setting, container, false);
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crccreate);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity4.changeFragment(new CRC_ADDPatient());
            }
        });
        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crcchangepwd);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity4.changeFragment(new CRC_ChangePWD());
            }
        });


        LinearLayout ltv = (LinearLayout) v.findViewById(R.id.crclogout);
        ltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editor editor = sp.edit();
                editor.putString("token", "");
                editor.putString("userType", "");
                editor.putString("userId", "");
                editor.putString("userPWD", "");
                editor.commit();
//				System.exit(0);

                Intent intent = new Intent();
                intent.setClass(CRC_Setting.this.getActivity().getApplication().getApplicationContext(), LoginActivity.class);
                CRC_Setting.this.getActivity().startActivity(intent);
                CRC_Setting.this.getActivity().finish();
            }
        });
        return v;
    }


}


