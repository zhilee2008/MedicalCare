package com.ysqm.medicalcare.cra;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.R;

public class CRA_ReportProjectMenuCRC extends Fragment {
    String projectId;
    String crcId;

    public CRA_ReportProjectMenuCRC(String projectId, String crcId) {
        this.projectId = projectId;
        this.crcId = crcId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cra_activity_report_projectmenu_crc, container, false);
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
        LinearLayout prv = (LinearLayout) v.findViewById(R.id.projectreportall1);
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRA_ReportProjectMenuCRC1(projectId, crcId));
            }
        });
        LinearLayout cv = (LinearLayout) v.findViewById(R.id.projectreportall2);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity3.changeFragment(new CRA_ReportProjectMenuCRC2(projectId, crcId));
            }
        });


        return v;
    }


}


