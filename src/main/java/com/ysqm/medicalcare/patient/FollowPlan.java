package com.ysqm.medicalcare.patient;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowPlan extends Fragment {

    FollowPlanThread fth;
    SharedPreferences sp;
    String token;

    private ListView listView = null;
    List<Map<String, Object>> listData;
    MyAdspter myAdspter;
    private ProgressDialog pd;

    String projectId;

    public FollowPlan(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        View v = inflater.inflate(R.layout.activity_followplan_list, container, false);
        pd = ProgressDialog.show(FollowPlan.this.getActivity(), "提示", "加载中，请稍后……");
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

        fth = new FollowPlanThread();
        fth.start();
        listView = (ListView) v.findViewById(R.id.list);

        return v;
    }

    class MyAdspter extends BaseAdapter implements ListAdapter {

        private List<Map<String, Object>> data;
        private LayoutInflater layoutInflater;
        private Context context;

        public MyAdspter(Context context, List<Map<String, Object>> data) {
            this.context = context;
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        /**
         * 组件集合，对应list.xml中的控件
         *
         * @author Administrator
         */
        public final class CRCOrdering {
            public ImageView image;
            public TextView title;
            //public Button view;
            public TextView info;
            public String patientId;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        /**
         * 获得某一位置的数据
         */
        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        /**
         * 获得唯一标识
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            CRCOrdering cRCOrdering = null;
            if (convertView == null) {
                cRCOrdering = new CRCOrdering();
                //获得组件，实例化组件
                convertView = layoutInflater.inflate(R.layout.activity_followplan_list_items, null);
                cRCOrdering.image = (ImageView) convertView.findViewById(R.id.image);
                cRCOrdering.title = (TextView) convertView.findViewById(R.id.title);
                cRCOrdering.info = (TextView) convertView.findViewById(R.id.info);

                convertView.setTag(cRCOrdering);
            } else {
                cRCOrdering = (CRCOrdering) convertView.getTag();
            }
            //绑定数据
            cRCOrdering.image.setBackgroundResource((Integer) data.get(position).get("image"));
            cRCOrdering.title.setText((String) data.get(position).get("title"));
            cRCOrdering.info.setText((String) data.get(position).get("info"));

            LinearLayout crcorderinglistitem = (LinearLayout) convertView.findViewById(R.id.crcallpatientlistitem);
            crcorderinglistitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String checkupId = data.get(position).get("checkupId").toString();
                    FragmentActivity0.changeFragment(new FollowPlanDetail(checkupId));
                }
            });

            return convertView;
        }

    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            listView.setAdapter(myAdspter);
            pd.dismiss();
            super.handleMessage(msg);
        }
    };

    class FollowPlanThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            Bundle data = new Bundle();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections.httpConnectionGet(Constants.CHECKUP, token);

            try {
                if (obj.getJSONArray("data").length() > 0) {
                    JSONArray OrderingList = obj.getJSONArray("data");
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < OrderingList.length(); i++) {
                        JSONObject OrderingObj = OrderingList.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
//			            map.put("image", R.drawable.patientgreen);
                        map.put("title", OrderingObj.getJSONObject("checkupDefine").get("name").toString());
                        map.put("info", OrderingObj.getJSONObject("project").get("name").toString() + ("null".equals(OrderingObj.get("recommendCheckupDate").toString()) ? "" : "_" + OrderingObj.get("recommendCheckupDate").toString()));
                        map.put("checkupId", OrderingObj.get("checkupId").toString());
                        map.put("image", R.drawable.patientgreen);
                        list.add(map);

                    }

                    listData = list;
                    myAdspter = new MyAdspter(getActivity(), listData);

                    msg.setData(data);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            FollowPlan.this.myHandler.sendMessage(msg);
        }
    }

}


