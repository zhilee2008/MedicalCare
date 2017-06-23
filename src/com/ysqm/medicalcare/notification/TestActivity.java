package com.ysqm.medicalcare.notification;

import java.util.List;

import com.ysqm.medicalcare.LoginActivity;
import com.ysqm.medicalcare.MainTabLayout;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.crc.CRC_Setting;

import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
		//Toast.makeText(this, "aaaaaa", 5).show();
		Intent intent = new Intent(); 
		intent.setClass(this,LoginActivity.class);
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
