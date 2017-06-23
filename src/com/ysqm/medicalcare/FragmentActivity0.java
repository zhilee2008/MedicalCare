package com.ysqm.medicalcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.cra.CRA_CurrentOrder;
import com.ysqm.medicalcare.cra.CRA_Report;
import com.ysqm.medicalcare.cralead.CRALEAD_CurrentOrder;
import com.ysqm.medicalcare.crc.CRC_CurrentOrder;
import com.ysqm.medicalcare.doctor.DOC_CurrentOrder;
import com.ysqm.medicalcare.patient.ProjectList;
import com.ysqm.medicalcare.patient.OrderSubmit;
import com.ysqm.medicalcare.utils.Constants;

/**
 *	功能描述：自定义TabHost
 */
public class FragmentActivity0 extends FragmentActivity {
	SharedPreferences sp;
    String userType;
	public static FragmentManager fm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	sp = PreferenceManager.getDefaultSharedPreferences(this);
		userType = sp.getString("userType","");
        super.onCreate(savedInstanceState);
//      //自定义标题
//      requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//      //设置标题为某个layout
//      getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        setContentView(R.layout.fragment_acitivity_0);
        fm = getSupportFragmentManager();
        
        switch(userType){
        	case "patient" : 
        		initFragment(new ProjectList());
        		break;
        	case "crc" : 
        		initFragment(new CRC_CurrentOrder());
        		break;
        	case "doctor" : 
        		initFragment(new DOC_CurrentOrder());
        		break;
        	case "cra" : 
        		initFragment(new CRA_CurrentOrder());
        		break;
        	case "cralead" : 
        		initFragment(new CRALEAD_CurrentOrder());
        		break;
        	default :
        		initFragment(new ProjectList());
        }
        
    }
    // 切換Fragment
    public static void changeFragment(Fragment f){
        changeFragment(f, false);
    }
    // 初始化Fragment(FragmentActivity中呼叫)
    public static void initFragment(Fragment f){
        changeFragment(f, true);
    }
    private static void changeFragment(Fragment f, boolean init){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment0, f);
        if(!init)
            ft.addToBackStack(null);
        ft.commit();
    }
}
