package com.ysqm.medicalcare.crc;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.FragmentActivity1;
import com.ysqm.medicalcare.FragmentActivity2;
import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.crc.CRC_HospitalList.Network;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class CRC_PatientGroup extends Fragment {
    private LinearLayout view;
    private ArrayList<HashMap<String, Object>> meumList_red;
    private ArrayList<HashMap<String, Object>> meumList_yellow;
    private ArrayList<HashMap<String, Object>> meumList_green;
    private ArrayList<HashMap<String, Object>> meumList_purple;
    private ArrayList<HashMap<String, Object>> meumList_gray;
    SimpleAdapter saMenuItem_red;
    SimpleAdapter saMenuItem_yellow;
    SimpleAdapter saMenuItem_green;
    SimpleAdapter saMenuItem_purple;
    SimpleAdapter saMenuItem_gray;
    GridView gridview_red;
    GridView gridview_yellow;
    GridView gridview_green;
    GridView gridview_purple;
    GridView gridview_gray;
    String token;
    SharedPreferences sp;
    private ProgressDialog pd;
    String tab = "1";
    String pro_hos = "";
    String projectId = "";
    String hospitalId = "";

    public CRC_PatientGroup(String tab, String pro_hos, String projectId, String hospitalId) {
        // TODO Auto-generated constructor stub
        this.tab = tab;
        this.pro_hos = pro_hos;
        this.projectId = projectId;
        this.hospitalId = hospitalId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");

        view = (LinearLayout) inflater.inflate(R.layout.crc_activity_patientgroup_list, container, false);
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
        pd = ProgressDialog.show(CRC_PatientGroup.this.getActivity(), "提示", "加载中，请稍后……");
        meumList_red = new ArrayList<HashMap<String, Object>>();
        meumList_yellow = new ArrayList<HashMap<String, Object>>();
        meumList_green = new ArrayList<HashMap<String, Object>>();
        meumList_purple = new ArrayList<HashMap<String, Object>>();
        meumList_gray = new ArrayList<HashMap<String, Object>>();

        gridview_red = (GridView) view.findViewById(R.id.GridViewPatient_red);
        gridview_red.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                    String patientId = meumList_red.get(arg2).get("patientId").toString();
                                                    if ("1".equals(tab)) {
                                                        FragmentActivity1.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                    } else {
                                                        FragmentActivity2.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                    }
                                                }
                                            }
        );
        gridview_yellow = (GridView) view.findViewById(R.id.GridViewPatient_yellow);
        gridview_yellow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                       String patientId = meumList_yellow.get(arg2).get("patientId").toString();
                                                       if ("1".equals(tab)) {
                                                           FragmentActivity1.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                       } else {
                                                           FragmentActivity2.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                       }
                                                   }
                                               }
        );
        gridview_green = (GridView) view.findViewById(R.id.GridViewPatient_green);
        gridview_green.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                      String patientId = meumList_green.get(arg2).get("patientId").toString();
                                                      if ("1".equals(tab)) {
                                                          FragmentActivity1.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                      } else {
                                                          FragmentActivity2.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                      }
                                                  }
                                              }
        );
        gridview_purple = (GridView) view.findViewById(R.id.GridViewPatient_purple);
        gridview_purple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                       String patientId = meumList_purple.get(arg2).get("patientId").toString();
                                                       if ("1".equals(tab)) {
                                                           FragmentActivity1.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                       } else {
                                                           FragmentActivity2.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                       }
                                                   }
                                               }
        );
        gridview_gray = (GridView) view.findViewById(R.id.GridViewPatient_gray);
        gridview_gray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                     String patientId = meumList_gray.get(arg2).get("patientId").toString();
                                                     if ("1".equals(tab)) {
                                                         FragmentActivity1.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                     } else {
                                                         FragmentActivity2.changeFragment(new CRC_PatientOrderList(tab, patientId));
                                                     }
                                                 }
                                             }
        );

        new Thread(new Network("name1")).start();

        saMenuItem_red = new SimpleAdapter(view.getContext(),
                meumList_red, //数据源
                R.layout.crc_activity_hospital_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage_hospital, R.id.ItemText});  //对应R的Id
        saMenuItem_yellow = new SimpleAdapter(view.getContext(),
                meumList_yellow, //数据源
                R.layout.crc_activity_hospital_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage_hospital, R.id.ItemText});  //对应R的Id
        saMenuItem_green = new SimpleAdapter(view.getContext(),
                meumList_green, //数据源
                R.layout.crc_activity_hospital_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage_hospital, R.id.ItemText});  //对应R的Id
        saMenuItem_purple = new SimpleAdapter(view.getContext(),
                meumList_purple, //数据源
                R.layout.crc_activity_hospital_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage_hospital, R.id.ItemText});  //对应R的Id
        saMenuItem_gray = new SimpleAdapter(view.getContext(),
                meumList_gray, //数据源
                R.layout.crc_activity_hospital_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage_hospital, R.id.ItemText});  //对应R的Id


        return view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");

            gridview_red.setAdapter(saMenuItem_red);
            gridview_yellow.setAdapter(saMenuItem_yellow);
            gridview_green.setAdapter(saMenuItem_green);
            gridview_purple.setAdapter(saMenuItem_purple);
            gridview_gray.setAdapter(saMenuItem_gray);
            pd.dismiss();
            //Log.i("val", "aaa");
        }
    };

    class Network implements Runnable {
        private String name;

        public Network(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            String name = this.name;
            Message msg = new Message();
            Bundle data = new Bundle();
            HttpConnections httpclient = new HttpConnections();
            JSONObject obj = httpclient.httpConnectionGet(Constants.PLAN + "?projectId=" + projectId + "&hospitalId=" + hospitalId, token);
            JSONArray objArray;
            try {
                objArray = obj.getJSONArray("data");
                for (int i = 0; i < objArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();

                    //map.put("ItemText", objArray.getJSONObject(i).getJSONObject("patient").get("name"));
                    map.put("ItemText", objArray.getJSONObject(i).getJSONObject("patient").get("name"));
                    map.put("patientId", objArray.getJSONObject(i).getJSONObject("patient").get("patientId"));
                    //ok 绿色， risk， warning， danger， stop
                    switch (objArray.getJSONObject(i).getJSONObject("patient").get("state").toString()) {
                        case "ok":
                            map.put("ItemImage", R.drawable.patientgreen);
                            meumList_green.add(map);
                            break;
                        case "risk":
                            map.put("ItemImage", R.drawable.patientblue);
                            meumList_purple.add(map);
                            break;
                        case "warning":
                            map.put("ItemImage", R.drawable.patientyellow);
                            meumList_yellow.add(map);
                            break;
                        case "danger":
                            map.put("ItemImage", R.drawable.patientred);
                            meumList_red.add(map);
                            break;
                        case "stop":
                            map.put("ItemImage", R.drawable.patientgray);
                            meumList_gray.add(map);
                            break;
                        default:
                            map.put("ItemImage", R.drawable.patientgreen);
                            meumList_green.add(map);
                            break;
                    }
                }
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            msg.setData(data);
            handler.sendMessage(msg);
        }

    }

}
