package com.ysqm.medicalcare.cra;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;

public class CRA_CurrentOrder extends Fragment {
//	SharedPreferences sp;
//    String userType;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
//		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
//		userType = sp.getString("userType","");
        View v = inflater.inflate(R.layout.cra_activity_order_list, container, false);

        LinearLayout ctv = (LinearLayout) v.findViewById(R.id.crccurrentordreqbutton);
        ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//		        View titlebar = inflater.inflate(R.layout.titlebar, container, false);
//				TextView b = (TextView) titlebar.findViewById(R.id.titletext);
//				System.out.println(b.getText().toString()+"+++++++++++");
//				b.setTextColor(R.color.black);
//				b.setText("aaaaaaaaaaaaa");
//				b.setVisibility(4);
//				Toast.makeText(getActivity(), b.getText(), 5);
                FragmentActivity0.changeFragment(new CRA_CurrentOrderedList());
            }
        });

        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crccurrentordbutton);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRA_CurrentConfirmedList());
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crccurrentcheckedbutton);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRA_CurrentCheckedList());
            }
        });
        LinearLayout hv = (LinearLayout) v.findViewById(R.id.historyfollowbutton);
        hv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRA_HistoryCompleteList());
            }
        });


        return v;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_current_order);
////        LinearLayout myorderbutton = (LinearLayout) findViewById(R.id.myordbutton);
////        myorderbutton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View arg0) {
////                Intent intent = new Intent();
////                intent.setClass(CurrentOrder.this,MyorderList.class);
////                CurrentOrder.this.startActivity(intent);
////                CurrentOrder.this.finish();
////            }
////        });
//
//    }
}
