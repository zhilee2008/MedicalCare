package com.ysqm.medicalcare.doctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;

public class DOC_CurrentOrder extends Fragment {
//	SharedPreferences sp;
//    String userType;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
//		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
//		userType = sp.getString("userType","");
        View v = inflater.inflate(R.layout.crc_activity_order_list, container, false);

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
                FragmentActivity0.changeFragment(new DOC_CurrentOrderedList());
            }
        });

        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crccurrentordbutton);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new DOC_CurrentConfirmedList());
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crccurrentcheckedbutton);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new DOC_CurrentCheckedList());
            }
        });
        LinearLayout hv = (LinearLayout) v.findViewById(R.id.historyfollowbutton);
        hv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new DOC_HistoryCompleteList());
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
