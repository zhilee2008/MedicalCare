package com.ysqm.medicalcare.doctor;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOC_ADDPatient extends Fragment {
    //	String patientId;
//	public CRC_Setting(String patientId){
//		this.patientId=patientId;
//	}
    private Spinner spinnerP;
    private Spinner spinnerH;
    private Spinner spinnerC;
    private List<String> data_listP;
    private List<String> data_listH;
    private List<String> data_listC;
    private ArrayAdapter<String> arr_adapterP;
    private ArrayAdapter<String> arr_adapterH;
    private ArrayAdapter<String> arr_adapterC;
    SharedPreferences sp;
    String token;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        view = inflater.inflate(R.layout.crc_activity_addpatient, container, false);
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


        Button tv = (Button) view.findViewById(R.id.confirmchangepwd);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://103.248.103.12:8080/track/service/common/changpassword
                //{"oldPassword":"passw0rd","newPassword":"passw1rd"}
                String p_username = "";
                String p_pwd = "";
                String p_name = "";
                String p_cardid = "";
                EditText vp_username = (EditText) view.findViewById(R.id.p_username);
                EditText vp_pwd = (EditText) view.findViewById(R.id.p_pwd);
                EditText vp_name = (EditText) view.findViewById(R.id.p_name);
//				EditText vp_cardid = (EditText)view.findViewById(R.id.p_cardid);
                p_username = vp_username.getText().toString().trim();
                p_pwd = vp_pwd.getText().toString().trim();
                p_name = vp_name.getText().toString().trim();
//				p_cardid	= vp_cardid.getText().toString().trim();	
                Map<String, String> mapParam = new HashMap<String, String>();
                mapParam.put("userId", p_username);
                mapParam.put("password", p_pwd);
                //mapParam.put("newPassword", p_name);
                //mapParam.put("oldPassword", p_cardid);

                Map<String, Object> mapParamO = new HashMap<String, Object>();
                mapParamO.put("user", mapParam);
                //renewpwd
                HttpConnections httpConnections = new HttpConnections();
                JSONObject obj = httpConnections.httpConnectionPost(Constants.ADDPATIENT, token, mapParamO, p_username, p_pwd, p_name);
                try {
                    if (Integer.parseInt(obj.get("statuCode").toString()) < Constants.SUCCESSCODE) {
                        Toast.makeText(DOC_ADDPatient.this.getActivity(), "添加受试者成功", 5).show();
                    } else {
                        Toast.makeText(DOC_ADDPatient.this.getActivity(), "添加受试者失败", 5).show();
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


