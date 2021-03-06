package com.ysqm.medicalcare.cralead;

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

public class CRALEAD_Setting extends Fragment {
    //	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        View v = inflater.inflate(R.layout.cra_activity_setting, container, false);
        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crachangepwd);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity4.changeFragment(new CRALEAD_ChangePWD());
            }
        });
        LinearLayout ltv = (LinearLayout) v.findViewById(R.id.cralogout);
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
                intent.setClass(CRALEAD_Setting.this.getActivity().getApplication().getApplicationContext(), LoginActivity.class);
                CRALEAD_Setting.this.getActivity().startActivity(intent);
                CRALEAD_Setting.this.getActivity().finish();
            }
        });
        return v;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_list);
//        LinearLayout currentorderbutton = (LinearLayout) findViewById(R.id.currentorderbutton);
//        currentorderbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent = new Intent();
//                intent.setClass(OrderList.this, CurrentOrder.class);
//                OrderList.this.startActivity(intent);
//                OrderList.this.finish();
//            }
//        });
//    }

}


