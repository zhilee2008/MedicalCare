package com.ysqm.medicalcare.crc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;

public class CRC_CurrentOrder extends Fragment {
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

                FragmentActivity0.changeFragment(new CRC_CurrentOrderedList());
            }
        });

        LinearLayout tv = (LinearLayout) v.findViewById(R.id.crccurrentordbutton);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRC_CurrentConfirmedList());
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crccurrentcheckedbutton);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRC_CurrentCheckedList());
            }
        });
        LinearLayout hv = (LinearLayout) v.findViewById(R.id.historyfollowbutton);
        hv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity0.changeFragment(new CRC_HistoryCompleteList());
            }
        });


        return v;
    }


}
