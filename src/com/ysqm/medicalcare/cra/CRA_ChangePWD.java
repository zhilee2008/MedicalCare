package com.ysqm.medicalcare.cra;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ysqm.medicalcare.cralead.CRALEAD_Setting;
import com.ysqm.medicalcare.patient.ChangePWD;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class CRA_ChangePWD extends Fragment {
//	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
	Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
	SharedPreferences sp;
	String token;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		token = sp.getString("token","");
		view = inflater.inflate(R.layout.activity_changepwd, container, false);
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
				EditText rnp = (EditText)view.findViewById(R.id.renewpwd);
				String renewPassword="";
				renewPassword	= rnp.getText().toString().trim();	
				if(!newPassword.equals(renewPassword)){
					Toast.makeText(CRA_ChangePWD.this.getActivity(), "两次输入新密码不相同，请重新输入", 5).show();
					return;
				}
				Matcher matcher  = pattern.matcher(newPassword);
				if(!matcher.matches()){
					Toast.makeText(CRA_ChangePWD.this.getActivity(), "新密码输入字符只能为字母A-Z，a-z，数字0-9中的组合，且区分大小写，请重新输入", 5).show();
					return;
				}
				Map<String, String> mapParam = new HashMap<String, String>();
         		mapParam.put("newPassword", newPassword);
                mapParam.put("oldPassword", oldPassword);
				//renewpwd
				 HttpConnections httpConnections = new HttpConnections();
          		JSONObject obj  = httpConnections.httpConnectionPost(Constants.CHANGEPWD, token,mapParam);
          		try {
						if(Integer.parseInt(obj.get("statuCode").toString())<Constants.SUCCESSCODE){
							Toast.makeText(CRA_ChangePWD.this.getActivity(), "密码修改成功", 5).show();
							FragmentActivity4.changeFragment(new CRA_Setting());
						}else{
							Toast.makeText(CRA_ChangePWD.this.getActivity(), "密码修改失败", 5).show();
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


