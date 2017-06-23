package com.ysqm.medicalcare.cralead;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.R;

public class CRALEAD_Report extends Fragment {
//	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cralead_activity_report, container, false);

        LinearLayout prv = (LinearLayout) v.findViewById(R.id.projectreport);
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRALEAD_ReportProjectList());
            }
        });
        LinearLayout ca = (LinearLayout) v.findViewById(R.id.crareport);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRALEAD_ReportCRA_CRAList());
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.crcreport);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRALEAD_ReportCRC_CRCList());
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


