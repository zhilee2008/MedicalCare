package com.ysqm.medicalcare.cralead;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

public class CRALEAD_CurrentCheckedList extends Fragment {
    OrderedThread odth;
    SharedPreferences sp;
    String token;
    private ProgressDialog pd;

    private ListView listView = null;
    List<Map<String, Object>> listData;

    MyAdspter myAdspter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        View v = inflater.inflate(R.layout.cra_activity_current_confirm_list, container, false);
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
        pd = ProgressDialog.show(CRALEAD_CurrentCheckedList.this.getActivity(), "提示", "加载中，请稍后……");
//		LinearLayout tv = (LinearLayout)v.findViewById(R.id.currentordbutton);
//        tv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//FragmentActivity0.changeFragment(new OrderList());
//			}
//		});
        odth = new OrderedThread();
        odth.start();

        listView = (ListView) v.findViewById(R.id.list);
//        listData=getData();  

        return v;
    }

//	public List<Map<String, Object>> getData(){
//        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();  
//        for (int i = 0; i < 50; i++) {
//            Map<String, Object> map=new HashMap<String, Object>();  
//            map.put("image", R.drawable.ic_launcher);  
//            map.put("title", "申请标题"+i);  
//            map.put("info", "申请时间"+i);
//            list.add(map);  
//        }  
//        return list;  
//    }  

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
                convertView = layoutInflater.inflate(R.layout.crc_activity_current_confirm_list_items, null);
                cRCOrdering.image = (ImageView) convertView.findViewById(R.id.image);
                cRCOrdering.title = (TextView) convertView.findViewById(R.id.title);
                //zujian.view=(Button)convertView.findViewById(R.id.view);
                cRCOrdering.info = (TextView) convertView.findViewById(R.id.info);

                convertView.setTag(cRCOrdering);
            } else {
                cRCOrdering = (CRCOrdering) convertView.getTag();
            }
            //绑定数据
            cRCOrdering.image.setBackgroundResource((Integer) data.get(position).get("image"));
            cRCOrdering.title.setText((String) data.get(position).get("title"));
            cRCOrdering.info.setText((String) data.get(position).get("info"));

            LinearLayout crcorderinglistitem = (LinearLayout) convertView.findViewById(R.id.crcorderedlistitem);
            crcorderinglistitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//						data.get(position).get("info");
                    String checkupId = data.get(position).get("checkupId").toString();
                    FragmentActivity0.changeFragment(new CRALEAD_CurrentChecked2Complete(checkupId));
                }
            });

            return convertView;
        }

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
            super.handleMessage(msg);

            if (myAdspter == null) {
                listView.setVisibility(View.INVISIBLE);
            } else {
                listView.setAdapter(myAdspter);
            }

            pd.dismiss();
        }
    };

    class OrderedThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            Bundle data = new Bundle();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections.httpConnectionGet(Constants.CHECKUP + Constants.STATE + Constants.CRC_CHECKED_STATE, token);
            myAdspter = null;
            try {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                if (obj.getJSONArray("data").length() > 0) {
                    JSONArray OrderingList = obj.getJSONArray("data");
                    for (int i = 0; i < OrderingList.length(); i++) {
                        JSONObject OrderingObj = OrderingList.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("title", OrderingObj.getJSONObject("project").get("name").toString() + "_" + OrderingObj.getJSONObject("hospital").getString("name").toString() + OrderingObj.getJSONObject("patient").getString("name").toString() + OrderingObj.get("patientId").toString());
                        map.put("info", OrderingObj.get("checkupPlanDate").toString());
                        map.put("checkupId", OrderingObj.get("checkupId").toString());
                        //ok 绿色， risk， warning， danger， stop
                        switch (OrderingObj.getJSONObject("patient").get("state").toString()) {
                            case "ok":
                                map.put("image", R.drawable.patientgreen);
                                break;
                            case "risk":
                                map.put("image", R.drawable.patientblue);
                                break;
                            case "warning":
                                map.put("image", R.drawable.patientyellow);
                                break;
                            case "danger":
                                map.put("image", R.drawable.patientred);
                                break;
                            case "stop":
                                map.put("image", R.drawable.patientgray);
                                break;
                            default:
                                map.put("image", R.drawable.patientgreen);
                                break;
                        }
                        list.add(map);
                    }
                }
                listData = list;
                myAdspter = new MyAdspter(getActivity(), listData);
                msg.setData(data);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            CRALEAD_CurrentCheckedList.this.myHandler.sendMessage(msg);
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_current_order);
////        LinearLayout myorderbutton = (LinearLayout) findViewById(R.id.myordbutton);
////        myorderbutton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View arg0) {
////                Intent intent = new Intent();
////                intent.setClass(CurrentOrder.this,MyorderList.class);
////                CurrentOrder.this.startActivity(intent);
////                CurrentOrder.this.finish();
////            }
////        });
//
//    }
}
