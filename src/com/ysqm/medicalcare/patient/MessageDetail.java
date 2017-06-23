package com.ysqm.medicalcare.patient;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.MainTabLayout;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class MessageDetail extends Fragment {
	
	String checkupId;
	
	public MessageDetail(String checkupId){
		this.checkupId=checkupId;
	}
	
	OrderThread oth;
	View view;
	SharedPreferences sp;
	String token;
	JSONArray checkitem=null;
	private ProgressDialog pd;  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		token = sp.getString("token","");
		view = inflater.inflate(R.layout.activity_message_detail, container, false);
		
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
		
		pd = ProgressDialog.show(MessageDetail.this.getActivity(), "提示", "加载中，请稍后……"); 
		oth = new OrderThread();
		oth.start();
        return view;
	}
	
	Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
//        	Intent intent = new Intent(); 
//        	Bundle bundle = new Bundle();
//			intent.setClass(LoginActivity.this,MainTabLayout.class);
//			bundle.putSerializable("role", msg.getData().getString("role"));
//			intent.putExtras(bundle);
//			LoginActivity.this.startActivity(intent);
//			LoginActivity.this.finish();
        	if(msg.getData().isEmpty()){
        		LinearLayout lyc = (LinearLayout)view.findViewById(R.id.p_currentordercontainer);
        		lyc.removeAllViews();
        		TextView notextView = new TextView(view.getContext());
        		notextView.setText( "暂无可预约项目" );
        		lyc.addView(notextView);
        		
        	}else{
	        	TextView timeV=(TextView)view.findViewById(R.id.p_ordertimetext);
	        	timeV.setText(msg.getData().getString("p_ordertimetext"));
	        	TextView addV=(TextView)view.findViewById(R.id.p_orderaddress);
	        	addV.setText(msg.getData().getString("p_orderaddress"));
	        	
        	}
        	pd.dismiss();
            super.handleMessage(msg);
        }
    };
     
    class OrderThread extends Thread{
        public void run() {
            // TODO Auto-generated method stub
              Message msg = new Message();
      		HttpConnections httpConnections = new HttpConnections();
     		JSONObject obj  =httpConnections.httpConnectionGet(Constants.CHECKUP+Constants.CHECKUPID+checkupId, token);
     		
     		
      	    try {
      	    	if(obj.getJSONArray("data").length()>0){
	      	    	JSONObject CurrentOrderObj = obj.getJSONArray("data").getJSONObject(0);
					Bundle bundle = new Bundle();
	//				bundle.putString("role", obj.getString("role"));
					bundle.putString("p_ordertimetext", CurrentOrderObj.getString("checkupPlanDate"));
					bundle.putString("p_orderaddress", CurrentOrderObj.getJSONObject("hospital").getString("name"));

	//				p_ordermention
	//				p_ordercomment
					msg.setData(bundle);
      	    	}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	    
              
      	  MessageDetail.this.myHandler.sendMessage(msg);
        }
    }

}
