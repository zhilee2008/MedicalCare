package com.ysqm.medicalcare.crc;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.ysqm.medicalcare.FragmentActivity1;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CRC_ProjectList extends Fragment {
    private View view;
    private ArrayList<HashMap<String, Object>> meumList;
    SimpleAdapter saMenuItem;
    GridView gridview;
    String token;
    SharedPreferences sp;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        view = inflater.inflate(R.layout.crc_activity_project_list, null);
        Button backbutton = (Button) view.findViewById(R.id.backbutton);
        backbutton.setVisibility(View.INVISIBLE);

        //GridView gridview = (GridView)  findViewById(R.id.GridView);
        pd = ProgressDialog.show(this.getActivity(), "提示", "加载中，请稍后……");
        new Thread(new Network("name")).start();

        gridview = (GridView) view.findViewById(R.id.GridView);

        meumList = new ArrayList<HashMap<String, Object>>();
        saMenuItem = new SimpleAdapter(inflater.getContext(),
                meumList, //数据源
                R.layout.crc_activity_project_list_items, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage, R.id.ItemText});  //对应R的Id

//添加Item到网格中

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                                //arg1.findViewById(R.)
//                meumList.get(arg2).get("projectId");
//                System.out.println("click index:"+meumList.get(arg2).get("projectId"));
//                Intent intent = new Intent(getActivity(),OrderList.class);
//                intent.setClass(getActivity(), OrderList.class);
//                startActivity(intent);
//            	ProjectInfo projectInfoList = new ProjectInfo();
//            	projectInfoList.setProjectId(meumList.get(arg2).get("projectId").toString());
                                                FragmentActivity1.changeFragment(new CRC_Project2HospitalList(meumList.get(arg2).get("projectId").toString()));
                                            }
                                        }
        );
        return view;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            gridview.setAdapter(saMenuItem);
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
            //HttpConnections httpclient = new HttpConnections();
            //httpclient.httpConnectionGet(Constants.PLAN,token);
            //ProjectService projectService=new ProjectService();
            HttpConnections httpclient = new HttpConnections();
            JSONObject obj = httpclient.httpConnectionGet(Constants.CRCPROJECT, token);
            JSONArray objArray;
            try {
                objArray = obj.getJSONArray("data");
                for (int i = 0; i < objArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemImage", R.drawable.project);
                    map.put("ItemText", objArray.getJSONObject(i).get("name"));
                    map.put("projectId", objArray.getJSONObject(i).get("projectId"));
                    meumList.add(map);
                }
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
//			ProjectService projectService = new ProjectService();
//			meumList = projectService.getProject(token);
//			for(int i = 1;i < 5;i++) {
//                HashMap<String, Object> map = new HashMap<String, Object>();
//                map.put("ItemImage", R.drawable.projects);
//                map.put("ItemText", "项目."+i);
//                map.put("projectId", "id:"+i);
//                meumList.add(map);
//            }
            msg.setData(data);
            handler.sendMessage(msg);
        }

    }

}
