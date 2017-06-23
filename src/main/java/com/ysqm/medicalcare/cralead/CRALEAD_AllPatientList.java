package com.ysqm.medicalcare.cralead;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ysqm.medicalcare.FragmentActivity2;
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

public class CRALEAD_AllPatientList extends Fragment {
    AllPatientThread ath;
    SharedPreferences sp;
    String token;
    EditText eSearch;
    View view;
    Handler myhandler = new Handler();

    private ListView listView = null;
    List<Map<String, Object>> listData;
    List<Map<String, Object>> allListData = new ArrayList<Map<String, Object>>();
    MyAdspter myAdspter;
    private ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (allListData != null && allListData.size() > 0) {
            allListData.clear();
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        view = inflater.inflate(R.layout.crc_activity_allpatient_list, container, false);
        pd = ProgressDialog.show(CRALEAD_AllPatientList.this.getActivity(), "提示", "加载中，请稍后……");
        ath = new AllPatientThread();
        ath.start();
        listView = (ListView) view.findViewById(R.id.list);

        return view;
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
                convertView = layoutInflater.inflate(R.layout.crc_activity_allpatient_list_items, null);
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
                    String patientId = data.get(position).get("patientId").toString();
                    FragmentActivity2.changeFragment(new CRALEAD_PatientOrderList("2", patientId));
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
            set_eSearch_TextChanged();
            pd.dismiss();
            super.handleMessage(msg);
        }
    };

    class AllPatientThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            Bundle data = new Bundle();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections.httpConnectionGet(Constants.PLAN, token);

            try {
                if (obj.getJSONArray("data").length() > 0) {
                    JSONArray OrderingList = obj.getJSONArray("data");
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < OrderingList.length(); i++) {
                        JSONObject OrderingObj = OrderingList.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
//			            map.put("image", R.drawable.patientgreen);
                        map.put("title", OrderingObj.getJSONObject("patient").get("name").toString() + "_" + OrderingObj.get("patientId").toString());
                        map.put("info", OrderingObj.getJSONObject("project").get("name").toString());
                        map.put("patientId", OrderingObj.get("patientId").toString());
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

                    listData = list;
                    allListData.addAll(list);
                    myAdspter = new MyAdspter(getActivity(), listData);

                    msg.setData(data);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            CRALEAD_AllPatientList.this.myHandler.sendMessage(msg);
        }
    }

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String data = eSearch.getText().toString();

            if ("".equals(data)) {
                listData.clear();
                listData.addAll(allListData);
            } else {
                List<Map<String, Object>> mDataSubs = new ArrayList<Map<String, Object>>();
                mDataSubs.addAll(allListData);
                listData.clear();
                getmDataSub(mDataSubs, data);
            }

            myAdspter.notifyDataSetChanged();

        }
    };

    private void getmDataSub(List<Map<String, Object>> mDataSubs, String data) {
        int length = mDataSubs.size();
        for (int i = 0; i < length; ++i) {
            if ((mDataSubs.get(i).get("title")).toString().contains(data) || (mDataSubs.get(i).get("info")).toString().contains(data)) {
                listData.add(mDataSubs.get(i));
            }
        }
    }

    private void set_eSearch_TextChanged() {
        eSearch = (EditText) view.findViewById(R.id.search_view);

        eSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
                    //ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
                    //ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
                }

                myhandler.post(eChanged);
            }
        });

    }

}
