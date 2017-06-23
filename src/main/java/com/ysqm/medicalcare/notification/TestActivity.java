package com.ysqm.medicalcare.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysqm.medicalcare.LoginActivity;

import cn.jpush.android.api.JPushInterface;

public class TestActivity extends Activity {

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        System.exit(0);
        //Toast.makeText(this, "aaaaaa", 5).show();
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        this.startActivity(intent);
//		CRC_Setting.this.getActivity().finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout lyc = new LinearLayout(this);
//		lyc.removeAllViews();
//		TextView notextView = new TextView(view.getContext());
//		notextView.setText( "暂无可预约项目" );
//		lyc.addView(notextView);
        TextView tv = new TextView(this);
        tv.setText("通知");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//	        tv.setText("Title : " + title + "  " + "Content : " + content);
            tv.setText("你有新的消息: " + content);
        }
        addContentView(tv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

}
