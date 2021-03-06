package com.ysqm.medicalcare.patient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.timepicker.JudgeDate;
import com.ysqm.medicalcare.timepicker.ScreenInfo;
import com.ysqm.medicalcare.timepicker.WheelMain;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderSubmit extends Fragment {

    OrderingThread oith;
    SharedPreferences sp;
    String token;

    private View view;

    private TextView ordertimetext = null;

    WheelMain wheelMain;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private boolean hasTime = true;
    private ProgressDialog pd;
    JSONArray checkitem = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_order_submit, null);
        pd = ProgressDialog.show(OrderSubmit.this.getActivity(), "提示",
                "加载中，请稍后……");
        ordertimetext = (TextView) view.findViewById(R.id.ordertimetext);
        Calendar calendar = Calendar.getInstance();

        String cm = (String.valueOf(calendar.get(Calendar.MONTH) + 1).length() > 1) ? String
                .valueOf(calendar.get(Calendar.MONTH) + 1) : "0"
                + String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String cd = (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
                .length() > 1) ? String.valueOf(calendar
                .get(Calendar.DAY_OF_MONTH)) : "0"
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String cH = (String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                .length() > 1) ? String.valueOf(calendar
                .get(Calendar.HOUR_OF_DAY)) : "0"
                + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String cM = (String.valueOf(calendar.get(Calendar.MINUTE)).length() > 1) ? String
                .valueOf(calendar.get(Calendar.MINUTE)) : "0"
                + String.valueOf(calendar.get(Calendar.MINUTE));

        ordertimetext.setText(calendar.get(Calendar.YEAR) + "-" + cm + "-" + cd
                + " " + cH + ":" + cM);
        LinearLayout ordertimebutton = (LinearLayout) view
                .findViewById(R.id.ordertimebutton);

        ordertimebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View timepickerview = inflater.inflate(
                        R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(OrderSubmit.this
                        .getActivity());
                wheelMain = new WheelMain(timepickerview, hasTime);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = ordertimetext.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                if (hasTime)
                    wheelMain.initDateTimePicker(year, month, day, hour, min);
                else
                    wheelMain.initDateTimePicker(year, month, day);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        ordertimetext.setText(wheelMain
                                                .getTime());
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        });

        view.findViewById(R.id.ordersubmitbutton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new AlertDialog.Builder(OrderSubmit.this.getActivity())
                                .setTitle("提示")
                                // 设置对话框标题
                                .setMessage("确认提交本次预约?")
                                // 设置显示的内容
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {// 添加确定按钮
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {// 确定按钮的响应事件
                                                // TODO Auto-generated method
                                                // stub
                                                Toast.makeText(
                                                        OrderSubmit.this
                                                                .getActivity(),
                                                        "预约成功", 5).show();
                                                Map<String, String> mapParam = new HashMap<String, String>();
                                                // mapParam.put("userId",
                                                // "crc1");
                                                // mapParam.put("password",
                                                // "passw0rd");
                                                mapParam.put(
                                                        "checkupId",
                                                        ((TextView) view
                                                                .findViewById(R.id.p_orderid))
                                                                .getText()
                                                                .toString());
                                                // mapParam.put("checkupId",
                                                // "1");
                                                // ordertimetext.getText().toString()
                                                mapParam.put("checkupPlanDate",
                                                        ordertimetext.getText()
                                                                .toString());
                                                // mapParam.put("state",
                                                // "ordered");
                                                HttpConnections httpConnections = new HttpConnections();
                                                JSONObject obj = httpConnections
                                                        .httpConnectionPut(
                                                                Constants.P_SUBMITORDER,
                                                                token, mapParam);
                                                try {
                                                    // if(Constants.PUTCODE.equals(obj.get("statuCode"))){
                                                    if (Integer.parseInt(obj
                                                            .get("statuCode")
                                                            .toString()) < Constants.SUCCESSCODE) {
                                                        LinearLayout lyc = (LinearLayout) view
                                                                .findViewById(R.id.p_currentordercontainer);
                                                        lyc.removeAllViews();
                                                        TextView notextView = new TextView(
                                                                view.getContext());
                                                        notextView
                                                                .setText("暂无可预约项目");
                                                        lyc.addView(notextView);
                                                    } else {
                                                        if ("501"
                                                                .equals(obj
                                                                        .get("statuCode")
                                                                        .toString())) {
                                                            Toast.makeText(
                                                                    OrderSubmit.this
                                                                            .getActivity(),
                                                                    "预约时间不匹配请重新选择",
                                                                    5).show();
                                                            return;
                                                        } else {
                                                            Toast.makeText(
                                                                    OrderSubmit.this
                                                                            .getActivity(),
                                                                    "预约失败，请联系研究护士或随后再试",
                                                                    5).show();
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch
                                                    // block
                                                    e.printStackTrace();
                                                }

                                            }

                                        })
                                .setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {// 添加返回按钮
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {// 响应事件
                                                // TODO Auto-generated method
                                                // stub
                                                // Log.i("alertdialog"," 请保存数据！");
                                            }
                                        }).show();// 在按键响应事件中显示此对话框
                    }

                });

        oith = new OrderingThread();
        oith.start();

        return view;
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.getData().isEmpty()) {
                LinearLayout lyc = (LinearLayout) view
                        .findViewById(R.id.p_currentordercontainer);
                lyc.removeAllViews();
                TextView notextView = new TextView(view.getContext());
                notextView.setText("暂无可预约项目");
                lyc.addView(notextView);

            } else {
                TextView planname = (TextView) view.findViewById(R.id.planname);
                planname.setText(msg.getData().getString("planname"));

                TextView ordertimereV = (TextView) view
                        .findViewById(R.id.ordertimeregion);
                String ob = msg.getData().getString("p_winstart").toString()
                        .substring(0, 10);
                String oe = msg.getData().getString("p_winend")
                        .substring(0, 10);
                ordertimereV.setText("根据随访规则请在" + ob + "至" + oe + "之间参加本次随访");
                TextView orderidV = (TextView) view
                        .findViewById(R.id.p_orderid);
                orderidV.setText(msg.getData().getString("p_orderid"));
                TextView addV = (TextView) view
                        .findViewById(R.id.p_orderaddress);
                addV.setText(msg.getData().getString("p_orderaddress"));
                TextView docV = (TextView) view
                        .findViewById(R.id.p_orderdoctor);
                docV.setText(msg.getData().getString("p_orderdoctor"));
                TextView contV = (TextView) view
                        .findViewById(R.id.p_ordercontact);
                contV.setText(msg.getData().getString("p_ordercontact"));
                TextView phoneV = (TextView) view
                        .findViewById(R.id.p_orderphone);
                phoneV.setText(msg.getData().getString("p_orderphone"));

                String p_ordermention = "";
                // JSONArray obj = (JSONArray)
                // msg.getData().getSerializable("p_checkupitem");
                LinearLayout llm = (LinearLayout) view
                        .findViewById(R.id.p_ordermentioncontainer);
                if (checkitem != null) {
                    TableLayout tableLayout = (TableLayout) view
                            .findViewById(R.id.orderMentionList);
                    if (checkitem.length() > 0) {
                        int c = 1;
                        for (int i = 0; i < checkitem.length(); i++) {
                            LinearLayout lla = new LinearLayout(
                                    view.getContext());
                            lla.setOrientation(0);
                            try {
                                TextView textView = new TextView(
                                        view.getContext());
                                textView.setText((checkitem.getJSONObject(i)
                                        .getString("name")).toString());

                                CheckBox checkBox = new CheckBox(
                                        view.getContext());
                                checkBox.setEnabled(false);

                                // lla.setGravity(Gravity.RIGHT);
                                lla.addView(checkBox);
                                tableLayout.addView(lla);
                                lla.addView(textView);

                                // p_ordermention=p_ordermention+","+((checkitem.getJSONObject(i).getString("notes"))==null?
                                // ""
                                // :(checkitem.getJSONObject(i).getString("notes")).toString());
                                if (!("null".equals(checkitem.getJSONObject(i)
                                        .getString("notes").toString()))) {
                                    TextView mtextView = new TextView(
                                            view.getContext());
                                    mtextView
                                            .setText(c++
                                                    + ":"
                                                    + (("null".equals(checkitem
                                                    .getJSONObject(i)
                                                    .getString("notes")
                                                    .toString())) ? ""
                                                    : checkitem
                                                    .getJSONObject(
                                                            i)
                                                    .getString(
                                                            "notes"))
                                                    .toString());
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
                        // TableLayout tableLayout = (TableLayout)
                        // view.findViewById(R.id.orderMentionList);
                        tableLayout.addView(lla);
                    }

                }

                // TextView
                // mentionV=(TextView)view.findViewById(R.id.p_ordermentioncontainer);
                // mentionV.setText(p_ordermention);
                TextView commentV = (TextView) view
                        .findViewById(R.id.p_ordercomment);
                commentV.setText(msg.getData().getString("p_ordercomment"));
            }
            pd.dismiss();
            LinearLayout lyc = (LinearLayout) view
                    .findViewById(R.id.p_currentordercontainer);
            lyc.setVisibility(View.VISIBLE);
            super.handleMessage(msg);
        }
    };

    class OrderingThread extends Thread {
        public void run() {
            // TODO Auto-generated method stub
            Message msg = new Message();
            HttpConnections httpConnections = new HttpConnections();
            JSONObject obj = httpConnections
                    .httpConnectionGet(
                            Constants.P_CURRENTORDER
                                    + URLEncoder
                                    .encode(Constants.P_CURRENTORDER_STATE),
                            token);

            try {

                if (obj.getJSONArray("data").length() > 0) {
                    JSONObject CurrentOrderObj = obj.getJSONArray("data")
                            .getJSONObject(0);
                    Bundle bundle = new Bundle();
                    bundle.putString(
                            "planname",
                            CurrentOrderObj.getJSONObject("checkupDefine")
                                    .get("name").toString());
                    bundle.putString("p_orderid",
                            CurrentOrderObj.getString("checkupId"));
                    bundle.putString("p_winstart",
                            CurrentOrderObj.getString("winStart"));
                    bundle.putString("p_winend",
                            CurrentOrderObj.getString("winEnd"));
                    // bundle.putString("role", obj.getString("role"));
                    // bundle.putString("p_ordertimetext",
                    // CurrentOrderObj.getString("checkupPlanDate"));
                    bundle.putString("p_orderaddress", CurrentOrderObj
                            .getJSONObject("hospital").getString("name"));
                    bundle.putString("p_orderdoctor", CurrentOrderObj
                            .getJSONObject("doctor").getString("name"));
                    bundle.putString("p_ordercontact", CurrentOrderObj
                            .getJSONObject("crc").getString("name"));
                    bundle.putString("p_orderphone", CurrentOrderObj
                            .getJSONObject("crc").getString("phone1"));
                    checkitem = CurrentOrderObj.getJSONObject("checkupDefine")
                            .getJSONArray("checkItems");

                    // p_ordermention
                    // p_ordercomment
                    msg.setData(bundle);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            OrderSubmit.this.myHandler.sendMessage(msg);
        }
    }

}
