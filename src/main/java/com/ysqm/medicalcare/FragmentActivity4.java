package com.ysqm.medicalcare;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ysqm.medicalcare.cra.CRA_Setting;
import com.ysqm.medicalcare.cralead.CRALEAD_Setting;
import com.ysqm.medicalcare.crc.CRC_Setting;
import com.ysqm.medicalcare.doctor.DOC_Setting;
import com.ysqm.medicalcare.patient.OrderSubmit;
import com.ysqm.medicalcare.patient.ProjectList;

public class FragmentActivity4 extends FragmentActivity {
    SharedPreferences sp;
    String userType;
    public static FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        userType = sp.getString("userType", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_4);
        fm = getSupportFragmentManager();
        switch (userType) {
            case "patient":
                initFragment(new OrderSubmit());
                break;
            case "crc":
                initFragment(new CRC_Setting());
                break;
            case "doctor":
                initFragment(new DOC_Setting());
                break;
            case "cra":
                initFragment(new CRA_Setting());
                break;
            case "cralead":
                initFragment(new CRALEAD_Setting());
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
        ft.replace(R.id.fragment4, f);
        if (!init)
            ft.addToBackStack(null);
        ft.commit();
    }
}
