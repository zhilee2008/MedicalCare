package com.ysqm.medicalcare.crc;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CRC_PatientCurrentConfirm2Checked extends Fragment {
    //	SharedPreferences sp;
//    String userType;
    SharedPreferences sp;
    String token;
    View view;

    private String checkupId;
    private String patientId;
    ConfirmedThread cth;
    JSONArray checkitem = null;

    public CRC_PatientCurrentConfirm2Checked(String checkupId, String patientId) {
        // TODO Auto-generated constructor stub
        this.checkupId = checkupId;
        this.patientId = patientId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
//		sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
//		userType = sp.getString("userType","");
        cth = new ConfirmedThread();
        cth.start();
        view = inflater.inflate(R.layout.crc_activity_current_confirm_checked, container, false);
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
        view.findViewById(R.id.crcconfirmedlistitem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(CRC_PatientCurrentConfirm2Checked.this.getActivity()).setTitle("提示")//设置对话框标题
                        .setMessage("确认完成本次检查?")//设置显放示的内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                Toast.makeText(CRC_PatientCurrentConfirm2Checked.this.getActivity(), "确认成功", 5).show();
                                //Toast.makeText(PayQuestionList.this, "积分扣除不成功,暂时无法查看答案", 5).show();
                                Map<String, String> mapParam = new HashMap<String, String>();
//                         		mapParam.put("userId", "crc1");
//                         		mapParam.put("password", "passw0rd");
                                mapParam.put("checkupId", CRC_PatientCurrentConfirm2Checked.this.checkupId);
                                //mapParam.put("checkupId", "1");
                                //ordertimetext.getText().toString()
                                TextView timeV = (TextView) view.findViewById(R.id.crc_ordertimetext);
                                mapParam.put("checkupDate", timeV.getText().toString());
                                TextView commentV = (TextView) view.findViewById(R.id.crc_ordercomment);
                                mapParam.put("comments", commentV.getText().toString());
                                HttpConnections httpConnections = new HttpConnections();
                                JSONObject obj = httpConnections.httpConnectionPut(Constants.CRC_CHECKUPCHECKED, token, mapParam);

                                try {
                                    if (Integer.parseInt(obj.get("statuCode").toString()) < Constants.SUCCESSCODE) {
                                        LinearLayout lyc = (LinearLayout) view.findViewById(R.id.crc_currentconfirmcontainer);
                                        lyc.removeAllViews();
                                        TextView notextView = new TextView(view.getContext());
                                        notextView.setText("暂无已预约项目");
                                        lyc.addView(notextView);
                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                        // Log.i("alertdialog"," 请保存数据！");
                    }
                }).show();//在按键响应事件中显示此对话框
            }

        });
//		LinearLayout tv = (LinearLayout)v.findViewById(R.id.crccurrentordbutton);
//        tv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				FragmentActivity0.changeFragment(new CRC_CurrentOrderedList());
//			}
//		});
        return view;
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            TextView planname = (TextView) view.findViewById(R.id.planname);
            planname.setText(msg.getData().getString("planname"));

            TextView pnameV = (TextView) view.findViewById(R.id.crc_patient);
            pnameV.setText(msg.getData().getString("crc_patient"));
            TextView pidV = (TextView) view.findViewById(R.id.crc_patient);
            pidV.setText(msg.getData().getString("crc_patientid"));
            TextView timeV = (TextView) view.findViewById(R.id.crc_ordertimetext);
            timeV.setText(msg.getData().getString("crc_ordertimetext"));
            TextView addV = (TextView) view.findViewById(R.id.crc_orderaddress);
            addV.setText(msg.getData().getString("crc_orderaddress"));
            TextView docV = (TextView) view.findViewById(R.id.crc_orderdoctor);
            docV.setText(msg.getData().getString("crc_orderdoctor"));
            TextView contV = (TextView) view.findViewById(R.id.crc_ordercontact);
            contV.setText(msg.getData().getString("crc_ordercontact"));
            TextView phoneV = (TextView) view.findViewById(R.id.crc_orderphone);
            phoneV.setText(msg.getData().getString("crc_orderphone"));
            LinearLayout llm = (LinearLayout) view.findViewById(R.id.crc_ordermentioncontainer);
            String crc_ordermention = "";
            if (checkitem != null) {
                if (checkitem.length() > 0) {
                    int c = 1;
                    for (int i = 0; i < checkitem.length(); i++) {
                        LinearLayout lla = new LinearLayout(view.getContext());
                        lla.setOrientation(0);
                        try {
                            TextView textView = new TextView(view.getContext());
                            textView.setText((checkitem.getJSONObject(i).getString("name")).toString());

                            CheckBox checkBox = new CheckBox(view.getContext());
                            checkBox.setEnabled(false);
//        					lla.setGravity(Gravity.RIGHT);
                            lla.addView(checkBox);
                            lla.addView(textView);
                            TableLayout tableLayout = (TableLayout) view.findViewById(R.id.crcorderMentionList);
                            tableLayout.addView(lla);
                            //crc_ordermention=crc_ordermention+","+(checkitem.getJSONObject(i).getString("notes"))==null?"":(checkitem.getJSONObject(i).getString("notes")).toString();
                            if (!("null".equals(checkitem.getJSONObject(i).getString("notes").toString()))) {
                                TextView mtextView = new TextView(view.getContext());
                                mtextView.setText(c++ + ":" + (("null".equals(checkitem.getJSONObject(i).getString("notes").toString())) ? "" : checkitem.getJSONObject(i).getString("notes")).toString());
                                llm.addView(mtextView);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
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
            TextView commentV = (TextView) view.findViewById(R.id.crc_ordercomment);
            commentV.setText(msg.getData().getString("crc_ordercomment"));

            super.handleMessage(msg);
        }
    };

    class ConfirmedThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections.httpConnectionGet(Constants.CHECKUP + Constants.STATE + Constants.CRC_CONFIRMED_STATE + Constants.PATIENTID + patientId, token);


            try {

                if (obj.getJSONArray("data").length() > 0) {
                    JSONObject CurrentOrderObj = obj.getJSONArray("data").getJSONObject(0);
                    Bundle bundle = new Bundle();
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
                    checkitem = CurrentOrderObj.getJSONObject("checkupDefine").getJSONArray("checkItems");
//    				crc_ordermention
//    				crc_ordercomment
                    msg.setData(bundle);
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            CRC_PatientCurrentConfirm2Checked.this.myHandler.sendMessage(msg);
        }
    }


}
