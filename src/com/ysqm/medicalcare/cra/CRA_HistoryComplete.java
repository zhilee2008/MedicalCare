package com.ysqm.medicalcare.cra;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class CRA_HistoryComplete extends Fragment {
//	SharedPreferences sp;
//    String userType;
	SharedPreferences sp;
	String token;
	View view;
	private ProgressDialog pd;  
	
	private String checkupId;
	ConfirmedThread cth;
	JSONArray checkitem=null;
	
	public CRA_HistoryComplete(String checkupId) {
		// TODO Auto-generated constructor stub
		this.checkupId=checkupId;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		pd = ProgressDialog.show(CRA_HistoryComplete.this.getActivity(), "提示", "加载中，请稍后……"); 
		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		token = sp.getString("token","");
//		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
//		userType = sp.getString("userType","");
		cth = new ConfirmedThread();
		cth.start();
		view= inflater.inflate(R.layout.cra_activity_history_complete, container, false);
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
        return view;
	}

	Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
        	TextView planname = (TextView) view.findViewById(R.id.planname);
			planname.setText(msg.getData().getString("planname"));
			
        	TextView pnameV=(TextView)view.findViewById(R.id.crc_patient);
        	pnameV.setText(msg.getData().getString("crc_patient"));
        	TextView pidV=(TextView)view.findViewById(R.id.crc_patient);
        	pidV.setText(msg.getData().getString("crc_patientid"));
        	TextView timeV=(TextView)view.findViewById(R.id.crc_ordertimetext);
        	timeV.setText(msg.getData().getString("crc_ordertimetext"));
        	TextView addV=(TextView)view.findViewById(R.id.crc_orderaddress);
        	addV.setText(msg.getData().getString("crc_orderaddress"));
        	TextView docV=(TextView)view.findViewById(R.id.crc_orderdoctor);
        	docV.setText(msg.getData().getString("crc_orderdoctor"));
        	TextView contV=(TextView)view.findViewById(R.id.crc_ordercontact);
        	contV.setText(msg.getData().getString("crc_ordercontact"));
        	TextView phoneV=(TextView)view.findViewById(R.id.crc_orderphone);
        	phoneV.setText(msg.getData().getString("crc_orderphone"));
        	LinearLayout llm = (LinearLayout)view.findViewById(R.id.crc_ordermentioncontainer); 
        	String crc_ordermention="";
        	if(checkitem!=null){
        		if(checkitem.length()>0){
        			int c=1;
        			for(int i=0;i<checkitem.length();i++){
        				LinearLayout lla = new LinearLayout(view.getContext());
        				lla.setOrientation(0);
        				try {
        					TextView textView = new TextView(view.getContext()); 
        					textView.setText((checkitem.getJSONObject(i).getString("name")).toString());
        					
        					CheckBox checkBox = new CheckBox(view.getContext());
        					if("checked".equals(checkitem.getJSONObject(i).getString("result").toString())){
        						checkBox.setChecked(true);
        					}
        					checkBox.setEnabled(false);
//        					lla.setGravity(Gravity.RIGHT);
        					lla.addView(checkBox);
        					lla.addView(textView);
        					TableLayout tableLayout = (TableLayout) view.findViewById(R.id.crcorderMentionList); 
        					tableLayout.addView(lla);
        					//crc_ordermention=crc_ordermention+","+(checkitem.getJSONObject(i).getString("notes"))==null?"":(checkitem.getJSONObject(i).getString("notes")).toString();
        					if(!("null".equals(checkitem.getJSONObject(i).getString("notes").toString()))){
        						TextView mtextView = new TextView(view.getContext()); 
        						mtextView.setText(c++ + ":"+( ("null".equals(checkitem.getJSONObject(i).getString("notes").toString())) ? "" : checkitem.getJSONObject(i).getString("notes")).toString());
        						llm.addView(mtextView);
        					}
        				} catch (JSONException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				} 
        			}
        		}else{
        			LinearLayout lla = new LinearLayout(view.getContext());
    				lla.setOrientation(0);
        			TextView textView = new TextView(view.getContext()); 
					textView.setText("无");
					lla.addView(textView);
					TableLayout tableLayout = (TableLayout) view.findViewById(R.id.crcorderMentionList); 
					tableLayout.addView(lla);
        		}
        		
        	}
        	
//        	TextView mentionV=(TextView)view.findViewById(R.id.crc_ordermention);
//        	mentionV.setText(crc_ordermention);
        	TextView commentV=(TextView)view.findViewById(R.id.crc_ordercomment);
        	commentV.setText(msg.getData().getString("crc_ordercomment"));
        	pd.dismiss();
            super.handleMessage(msg);
        }
    };
     
    class ConfirmedThread extends Thread{
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
      		HttpConnections httpConnections = new HttpConnections();
     		JSONObject obj  =httpConnections.httpConnectionGet(Constants.CHECKUP+Constants.CHECKUPID+checkupId, token);
     		
      	    try {
      	    	
      	    	if(obj.getJSONArray("data").length()>0){
      	    		JSONObject CurrentOrderObj = obj.getJSONArray("data").getJSONObject(0);
    				Bundle bundle = new Bundle();
//    				bundle.putString("crc_orderid", CurrentOrderObj.getString("checkupId"));
//    				bundle.putString("role", obj.getString("role"));
    				bundle.putString(
							"planname",
							CurrentOrderObj.getJSONObject("checkupDefine")
									.get("name").toString());
    				bundle.putString("crc_patient", CurrentOrderObj.getJSONObject("patient").getString("name"));
    				bundle.putString("crc_patientid", CurrentOrderObj.getString("patientId"));
    				bundle.putString("crc_ordertimetext", CurrentOrderObj.getString("checkupPlanDate"));
    				bundle.putString("crc_orderaddress", CurrentOrderObj.getJSONObject("hospital").getString("name").toString());
    				bundle.putString("crc_orderdoctor", CurrentOrderObj.getJSONObject("doctor").getString("name"));
    				bundle.putString("crc_ordercontact", CurrentOrderObj.getJSONObject("crc").getString("name"));
    				bundle.putString("crc_orderphone", CurrentOrderObj.getJSONObject("crc").getString("phone1"));
    				bundle.putString("crc_ordercomment", CurrentOrderObj.getString("comments"));
    				//http://103.248.103.12:8080/track/service/common/checkitem?checkupId=326
    				
    				checkitem=CurrentOrderObj.getJSONObject("checkupDefine").getJSONArray("checkItems");
//    				crc_ordermention
//    				crc_ordercomment
    				msg.setData(bundle);
      	    	}
      	    	JSONObject objcheckitem  =httpConnections.httpConnectionGet(Constants.CHECKITEM+Constants.CHECKUPID+checkupId, token);
      	    	if(objcheckitem.getJSONArray("data").length()>0){
      	    		checkitem = objcheckitem.getJSONArray("data");
      	    	}
      	    	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	    
              
      	  CRA_HistoryComplete.this.myHandler.sendMessage(msg);
        }
    }


}
