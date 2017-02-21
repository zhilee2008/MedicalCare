package com.ysqm.medicalcare.doctor;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.FragmentActivity2;
import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.FragmentActivity4;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.crc.CRC_Setting;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class DOC_ChangePWD extends Fragment {
//	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
	SharedPreferences sp;
	String token;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		token = sp.getString("token","");
		view = inflater.inflate(R.layout.crc_activity_changepwd, container, false);
		Button backButton = (Button) view.findViewById(R.id.backbutton);
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
		Button tv = (Button)view.findViewById(R.id.confirmchangepwd);
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//http://103.248.103.12:8080/track/service/common/changpassword
				//{"oldPassword":"passw0rd","newPassword":"passw1rd"}
				String oldPassword="";
				String newPassword="";
				EditText op = (EditText)view.findViewById(R.id.oldpwd);
				EditText np = (EditText)view.findViewById(R.id.newpwd);
				oldPassword = op.getText().toString().trim();
				newPassword	= np.getText().toString().trim();	
				Map<String, String> mapParam = new HashMap<String, String>();
         		mapParam.put("newPassword", newPassword);
                mapParam.put("oldPassword", oldPassword);
				//renewpwd
				 HttpConnections httpConnections = new HttpConnections();
          		JSONObject obj  = httpConnections.httpConnectionPost(Constants.CHANGEPWD, token,mapParam);
          		try {
						if(Integer.parseInt(obj.get("statuCode").toString())<Constants.SUCCESSCODE){
							Toast.makeText(DOC_ChangePWD.this.getActivity(), "密码修改成功", 5).show();
							FragmentActivity4.changeFragment(new DOC_Setting());
						}else{
							Toast.makeText(DOC_ChangePWD.this.getActivity(), "密码修改失败", 5).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				//FragmentActivity2.changeFragment(new ChangePWD());
			}
		});
        return view;
	}


}


