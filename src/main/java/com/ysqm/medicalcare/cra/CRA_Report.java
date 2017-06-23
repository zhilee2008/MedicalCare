package com.ysqm.medicalcare.cra;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.R;

public class CRA_Report extends Fragment {
//	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cra_activity_report, container, false);

        LinearLayout prv = (LinearLayout) v.findViewById(R.id.projectreport);
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRA_ReportProjectList());
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crcreport);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRA_ReportCRC_CRCList());
            }
        });
//        LinearLayout cv = (LinearLayout)v.findViewById(R.id.crcreport);
//        cv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				FragmentActivity3.changeFragment(new CRA_ReportPatient());
//			}
//		});
        return v;
    }


}


