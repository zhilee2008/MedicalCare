package com.ysqm.medicalcare.doctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity4;
import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.R;

public class DOC_Setting extends Fragment {
    //	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        View v = inflater.inflate(R.layout.doc_activity_setting, container, false);

        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crcchangepwd);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity4.changeFragment(new DOC_ChangePWD());
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
                intent.setClass(DOC_Setting.this.getActivity().getApplication().getApplicationContext(), LoginActivity.class);
                DOC_Setting.this.getActivity().startActivity(intent);
                DOC_Setting.this.getActivity().finish();
            }
        });
        return v;
    }


}


