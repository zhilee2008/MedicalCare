package com.ysqm.medicalcare;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ysqm.medicalcare.cra.CRA_Report;
import com.ysqm.medicalcare.cralead.CRALEAD_Report;
import com.ysqm.medicalcare.crc.CRC_AllPatientList;
import com.ysqm.medicalcare.doctor.DOC_AllPatientList;
import com.ysqm.medicalcare.patient.OrderSubmit;
import com.ysqm.medicalcare.patient.ProjectList;

public class FragmentActivity3 extends FragmentActivity {
    SharedPreferences sp;
    String userType;
    public static FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        userType = sp.getString("userType", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_3);
        fm = getSupportFragmentManager();
        switch (userType) {
            case "patient":
                initFragment(new OrderSubmit());
                break;
            case "crc":
                initFragment(new CRC_AllPatientList());
                break;
            case "doctor":
                initFragment(new DOC_AllPatientList());
                break;
            case "cra":
                initFragment(new CRA_Report());

                break;
            case "cralead":
                initFragment(new CRALEAD_Report());

                break;
            default:
                initFragment(new ProjectList());
        }
    }

    public static void changeFragment(Fragment f) {
        changeFragment(f, false);
    }

    public static void initFragment(Fragment f) {
        changeFragment(f, true);
    }

    private static void changeFragment(Fragment f, boolean init) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment3, f);
        if (!init)
            ft.addToBackStack(null);
        ft.commit();
    }
}
