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

public class FollowPlanDetail extends Fragment {
	
	String checkupId;
	
	public FollowPlanDetail(String checkupId){
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
		view = inflater.inflate(R.layout.activity_followplan_detail, container, false);
		
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
		
		pd = ProgressDialog.show(FollowPlanDetail.this.getActivity(), "提示", "加载中，请稍后……"); 
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
        		TextView planname = (TextView) view.findViewById(R.id.planname);
				planname.setText(msg.getData().getString("planname"));
				
	        	TextView timeV=(TextView)view.findViewById(R.id.p_ordertimetext);
	        	timeV.setText(msg.getData().getString("p_ordertimetext"));
	        	TextView addV=(TextView)view.findViewById(R.id.p_orderaddress);
	        	addV.setText(msg.getData().getString("p_orderaddress"));
	        	TextView docV=(TextView)view.findViewById(R.id.p_orderdoctor);
	        	docV.setText(msg.getData().getString("p_orderdoctor"));
	        	TextView contV=(TextView)view.findViewById(R.id.p_ordercontact);
	        	contV.setText(msg.getData().getString("p_ordercontact"));
	        	TextView phoneV=(TextView)view.findViewById(R.id.p_orderphone);
	        	phoneV.setText(msg.getData().getString("p_orderphone"));
	        	TextView cV=(TextView)view.findViewById(R.id.p_ordercomment);
	        	cV.setText(msg.getData().getString("p_ordercomment"));
	        	LinearLayout llm = (LinearLayout)view.findViewById(R.id.p_ordermentioncontainer); 
	        	String p_ordermention="";
	//        	JSONArray obj = (JSONArray) msg.getData().getSerializable("p_checkupitem");
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
	        					checkBox.setEnabled(false);
//	        					lla.setGravity(Gravity.RIGHT);
	        					lla.addView(checkBox);
	        					lla.addView(textView);
	        					TableLayout tableLayout = (TableLayout) view.findViewById(R.id.orderMentionList); 
	        					tableLayout.addView(lla);
	        					//p_ordermention=p_ordermention+","+(checkitem.getJSONObject(i).getString("notes"))==null?"":(checkitem.getJSONObject(i).getString("notes")).toString();
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
						TableLayout tableLayout = (TableLayout) view.findViewById(R.id.orderMentionList); 
						tableLayout.addView(lla);
	        		}
	        		
	        	}
	        	
	        	TextView mentionV=(TextView)view.findViewById(R.id.p_ordermention);
	        	mentionV.setText(p_ordermention);
	        	TextView commentV=(TextView)view.findViewById(R.id.p_ordercomment);
	        	commentV.setText(msg.getData().getString("p_ordercomment"));
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
					bundle.putString(
							"planname",
							CurrentOrderObj.getJSONObject("checkupDefine")
									.get("name").toString());
	//				bundle.putString("role", obj.getString("role"));
					bundle.putString("p_ordertimetext", CurrentOrderObj.getString("checkupPlanDate"));
					bundle.putString("p_orderaddress", CurrentOrderObj.getJSONObject("hospital").getString("name"));
					bundle.putString("p_orderdoctor", CurrentOrderObj.getJSONObject("doctor").getString("name"));
					bundle.putString("p_ordercontact", CurrentOrderObj.getJSONObject("crc").getString("name"));
					bundle.putString("p_orderphone", CurrentOrderObj.getJSONObject("crc").getString("phone1"));
					checkitem=CurrentOrderObj.getJSONObject("checkupDefine").getJSONArray("checkItems");
					bundle.putString("p_ordercomment", CurrentOrderObj.getString("comments"));
	//				p_ordermention
	//				p_ordercomment
					msg.setData(bundle);
      	    	}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	    
              
      	  FollowPlanDetail.this.myHandler.sendMessage(msg);
        }
    }

}
