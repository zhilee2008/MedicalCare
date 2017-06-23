package com.ysqm.medicalcare;


import com.ysqm.medicalcare.cra.CRA_AllPatientList;
import com.ysqm.medicalcare.cra.CRA_Report;
import com.ysqm.medicalcare.cralead.CRALEAD_AllPatientList;
import com.ysqm.medicalcare.crc.CRC_AllPatientList;
import com.ysqm.medicalcare.crc.CRC_HospitalList;
import com.ysqm.medicalcare.crc.CRC_ProjectList;
import com.ysqm.medicalcare.doctor.DOC_HospitalList;
import com.ysqm.medicalcare.patient.OrderSubmit;
import com.ysqm.medicalcare.patient.ProjectList;
import com.ysqm.medicalcare.patient.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentActivity2 extends FragmentActivity {
    SharedPreferences sp;
    String userType;
    public static FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        userType = sp.getString("userType", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_2);
        fm = getSupportFragmentManager();
        switch (userType) {
            case "patient":
                initFragment(new Setting());
                break;
            case "crc":
                initFragment(new CRC_HospitalList());
                break;
            case "doctor":
                initFragment(new DOC_HospitalList());
                break;
            case "cra":
                initFragment(new CRA_AllPatientList());
                break;
            case "cralead":
                initFragment(new CRALEAD_AllPatientList());
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
        ft.replace(R.id.fragment2, f);
        if (!init)
            ft.addToBackStack(null);
        ft.commit();
    }
}
