package com.ysqm.medicalcare.patient;

import java.net.URLEncoder;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.net.Uri;
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
import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.MainTabLayout;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class Contact extends Fragment {


    OrderThread oth;
    View view;
    SharedPreferences sp;
    String token;
    JSONArray checkitem = null;
    private ProgressDialog pd;
    String telnumber = "";
    TextView telV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        view = inflater.inflate(R.layout.activity_contact, container, false);

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

        pd = ProgressDialog.show(Contact.this.getActivity(), "提示", "加载中，请稍后……");
        oth = new OrderThread();
        oth.start();


        telV = (TextView) view.findViewById(R.id.telnumber);
        telV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        telV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

//				Intent intent = newIntent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);

                telnumber = telV.getText().toString();
                if ("".equals(telnumber)) {
                    Toast.makeText(Contact.this.getActivity(), "暂无联系方式", 5).show();
                } else {
                    new AlertDialog.Builder(Contact.this.getActivity()).setTitle("提示")//设置对话框标题
                            .setMessage("是否拨通以下号码:" + telnumber)//设置显示的内容
                            .setPositiveButton("拨通", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + telnumber));
                                    startActivity(intent);
                                }

                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            // TODO Auto-generated method stub
                            // Log.i("alertdialog"," 请保存数据！");
                        }
                    }).show();//在按键响应事件中显示此对话框
                }


            }
        });

        return view;
    }

    Handler myHandler = new Handler() {
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
            if (msg.getData().isEmpty()) {
                LinearLayout lyc = (LinearLayout) view.findViewById(R.id.p_currentordercontainer);
                lyc.removeAllViews();
                TextView notextView = new TextView(view.getContext());
                notextView.setText("暂无可预约项目");
                lyc.addView(notextView);

            } else {
                TextView timeV = (TextView) view.findViewById(R.id.p_ordertimetext);
                timeV.setText(msg.getData().getString("p_crcname"));
                TextView addV = (TextView) view.findViewById(R.id.telnumber);
                addV.setText(msg.getData().getString("p_crcphone"));
//	        	addV.setText("10086");
            }
            pd.dismiss();
            super.handleMessage(msg);
        }
    };

    class OrderThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections.httpConnectionGet(Constants.CHECKUP, token);


            try {
                if (obj.getJSONArray("data").length() > 0) {
                    JSONObject CurrentOrderObj = obj.getJSONArray("data").getJSONObject(0);
                    Bundle bundle = new Bundle();
                    //				bundle.putString("role", obj.getString("role"));
                    bundle.putString("p_crcphone", CurrentOrderObj.getJSONObject("crc").getString("phone1"));
                    bundle.putString("p_crcname", CurrentOrderObj.getJSONObject("crc").getString("name"));

                    //				p_ordermention
                    //				p_ordercomment
                    msg.setData(bundle);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Contact.this.myHandler.sendMessage(msg);
        }
    }

}
