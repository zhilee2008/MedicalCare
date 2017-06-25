package com.ysqm.medicalcare.crc;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity1;
import com.ysqm.medicalcare.FragmentActivity2;
import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CRC_PatientOrderGroupList extends Fragment {
    PatientStateThread pth;
    SharedPreferences sp;
    String currentCheckupId = "";
    String currentCheckupState = "";
    String token;
    String patientId;
    String tab;

    public CRC_PatientOrderGroupList(String tab, String patientId) {
        this.patientId = patientId;
        this.tab = tab;
    }

    JSONObject objOR = null;
    JSONObject objCO = null;
    JSONObject objCH = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        View v = inflater.inflate(R.layout.crc_activity_patientgroup_order_list, container, false);
        Button backButton = (Button) v.findViewById(R.id.backbutton);
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

        pth = new PatientStateThread();
        pth.start();

        LinearLayout tv = (LinearLayout) v.findViewById(R.id.currentordbutton);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (objOR != null && objOR.getJSONArray("data").length() > 0) {
                        if ("1".equals(tab)) {
                            //项目
                            //医院
                            if ("ordered".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity1.changeFragment(new CRC_PatientCurrentOrdered2Confirm(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("2".equals(tab)) {
                            //医院
                            if ("ordered".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity2.changeFragment(new CRC_PatientCurrentOrdered2Confirm(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("3".equals(tab)) {

                            if ("ordered".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity3.changeFragment(new CRC_PatientCurrentOrdered2Confirm(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        }
                    } else {
                        Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        LinearLayout ctv = (LinearLayout) v.findViewById(R.id.crccurrentconfirmbutton);
        ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (objCO != null && objCO.getJSONArray("data").length() > 0) {
                        if ("1".equals(tab)) {
                            //项目
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity1.changeFragment(new CRC_PatientCurrentConfirm2Checked(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("2".equals(tab)) {
                            //医院
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity2.changeFragment(new CRC_PatientCurrentConfirm2Checked(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("3".equals(tab)) {
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity3.changeFragment(new CRC_PatientCurrentConfirm2Checked(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        }
                    } else {
                        Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        LinearLayout chtv = (LinearLayout) v.findViewById(R.id.crccurrentcheckedbutton);
        chtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (objCH != null && objCH.getJSONArray("data").length() > 0) {
                        if ("1".equals(tab)) {
                            //项目
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity1.changeFragment(new CRC_PatientCurrentChecked2Complete(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("2".equals(tab)) {
                            //医院
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {

                                    FragmentActivity2.changeFragment(new CRC_PatientCurrentChecked2Complete(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        } else if ("3".equals(tab)) {
                            if ("confirmed".equals(currentCheckupState)) {
                                //受试者
                                if (!("".equals(currentCheckupId))) {
                                    FragmentActivity3.changeFragment(new CRC_PatientCurrentChecked2Complete(currentCheckupId, patientId));
                                }
                            } else {
                                Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                            }

                        }
                    } else {
                        Toast.makeText(v.getContext(), "当前没有待确认的预约信息", 5).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });
        return v;
    }

//	Handler myHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//        	
//            super.handleMessage(msg);
//        }
//    };

    class PatientStateThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            HttpConnections httpConnections = new HttpConnections();
            //JSONObject obj  =httpConnections.httpConnectionGet(Constants.P_CURRENTORDER+URLEncoder.encode(Constants.P_ORDEREDANDCONFIRMED_STATE)+"&patientId="+patientId, token);
//          	public static String P_CURRENTORDERED_STATE="ordered";
//        	public static String P_CURRENTCONFIRM_STATE="confirmed";
//        	public static String P_CURRENTCHECKED_STATE="checked";
            objOR = httpConnections.httpConnectionGet(Constants.P_CURRENTORDER + Constants.P_CURRENTORDERED_STATE + "&patientId=" + patientId, token);
            objCO = httpConnections.httpConnectionGet(Constants.P_CURRENTORDER + Constants.P_CURRENTCONFIRM_STATE + "&patientId=" + patientId, token);
            objCH = httpConnections.httpConnectionGet(Constants.P_CURRENTORDER + Constants.P_CURRENTCHECKED_STATE + "&patientId=" + patientId, token);
            JSONObject obj = null;
            try {

                if (objOR.getJSONArray("data").length() > 0) {
                    obj = objOR;
                } else if (objCO.getJSONArray("data").length() > 0) {
                    obj = objCO;
                } else if (objCH.getJSONArray("data").length() > 0) {
                    obj = objCH;
                }
                if (obj != null) {
                    JSONArray OrderingList = obj.getJSONArray("data");
                    if (OrderingList.length() > 0) {
                        JSONObject OrderingObj = OrderingList.getJSONObject(0);
                        currentCheckupId = OrderingObj.get("checkupId").toString();
                        currentCheckupState = OrderingObj.get("state").toString();
                    }
                }

      	    	/*if(obj.getJSONArray("data").length()>0){
      	    		JSONArray OrderingList = obj.getJSONArray("data");
      	    		if(OrderingList.length()>0){
      	    			JSONObject OrderingObj=OrderingList.getJSONObject(0);
      	    			currentCheckupId=OrderingObj.get("checkupId").toString();
      	    			currentCheckupState=OrderingObj.get("state").toString();
      	    		}
//      	    		for(int i=0;i<OrderingList.length();i++){
//      	    			JSONObject OrderingObj= OrderingList.getJSONObject(i);
////			            map.put("image", R.drawable.patientgreen);
////			            map.put("title", OrderingObj.getJSONObject("project").get("name").toString()+OrderingObj.get("patientId").toString());  
////			            map.put("info", OrderingObj.get("patientId").toString());
////			           map.put("patientId", OrderingObj.get("patientId").toString());
//      	    			
//      	    		}
					
      	    	}*/
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


//      	  CRC_PatientOrderList.this.myHandler.sendMessage(msg);
        }
    }

}


