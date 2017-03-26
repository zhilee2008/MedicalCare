package com.ysqm.medicalcare;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Instrumentation;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

import com.ysqm.medicalcare.notification.*;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.patient.OrderSubmit;

/**
 * 功能描述：自定义TabHost
 */
public class MainTabLayout extends InstrumentedActivity {
	
	public static Button backButton;

	// private String userType="crc";

	private String PATIENT = "patient";
	private String CRC = "crc";
	private String CRA = "cra";
	private String DOCTOR = "doctor";
	private String CRALEAD = "cralead";
	private String userType = "";

	LocalActivityManager lam;
	// 定义FragmentTabHost对象
	public static TabHost mTabHost;
	// 定义一个布局
	private LayoutInflater layoutInflater;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		/*// 自定义标题
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		// 设置标题为某个layout
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);
		backButton = (Button) findViewById(R.id.backbutton);
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
		});*/

		
		setContentView(R.layout.main_tab_layout);

		Intent intent = this.getIntent();
		userType = (String) intent.getSerializableExtra("role");

		// 实例化TabHost对象，得到TabHost
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		// mTabHost.setup(this, getFragmentManager(), R.id.realtabcontent);
		lam = new LocalActivityManager(MainTabLayout.this, false);
		lam.dispatchCreate(savedInstanceState);

		JPushInterface.init(getApplicationContext());

		mTabHost.setup(lam);

		if (userType.equals(this.PATIENT)) {
			// patient
			// 定义数组来存放按钮图片
			int mImageViewArray[] = { R.drawable.tab_query_btn,
					R.drawable.tab_reserve_btn, R.drawable.tab_set_btn };
			// Tab选项卡的文字
			String mTextviewArray[] = { "查询", "预约", "设置" };

			Class fragmentArray[] = { FragmentActivity0.class,
					FragmentActivity1.class, FragmentActivity2.class };
			initView(fragmentArray, mImageViewArray, mTextviewArray);
		} else if (userType.equals(this.CRC)){
			int mImageViewArray[] = { R.drawable.tab_reserve_btn,
					R.drawable.tab_project_btn, R.drawable.tab_hospital_btn,
					R.drawable.tab_patient_btn, R.drawable.tab_set_btn };
			// Tab选项卡的文字
			String mTextviewArray[] = { "任务", "项目", "医院", "受试者", "设置" };

			Class fragmentArray[] = { FragmentActivity0.class,
					FragmentActivity1.class, FragmentActivity2.class,
					FragmentActivity3.class, FragmentActivity4.class };
			initView(fragmentArray, mImageViewArray, mTextviewArray);
		} else if (userType.equals(this.DOCTOR)){
			int mImageViewArray[] = { R.drawable.tab_reserve_btn,
					R.drawable.tab_project_btn, R.drawable.tab_hospital_btn,
					R.drawable.tab_patient_btn, R.drawable.tab_set_btn };
			// Tab选项卡的文字
			String mTextviewArray[] = { "任务", "项目", "医院", "受试者", "设置" };

			Class fragmentArray[] = { FragmentActivity0.class,
					FragmentActivity1.class, FragmentActivity2.class,
					FragmentActivity3.class, FragmentActivity4.class };
			initView(fragmentArray, mImageViewArray, mTextviewArray);
		} else if (userType.equals(this.CRA)){
			int mImageViewArray[] = { R.drawable.tab_reserve_btn,R.drawable.tab_project_btn,R.drawable.tab_patient_btn,R.drawable.tab_report_btn, R.drawable.tab_set_btn };
			// Tab选项卡的文字
			String mTextviewArray[] = {"任务", "项目","受试者","报告", "设置" };

			Class fragmentArray[] = { FragmentActivity0.class,
					FragmentActivity1.class,FragmentActivity2.class,FragmentActivity3.class,FragmentActivity4.class};
			initView(fragmentArray, mImageViewArray, mTextviewArray);
			
		} else if (userType.equals(this.CRALEAD)){
			int mImageViewArray[] = { R.drawable.tab_reserve_btn,R.drawable.tab_project_btn,R.drawable.tab_patient_btn,R.drawable.tab_report_btn, R.drawable.tab_set_btn };
			// Tab选项卡的文字
			String mTextviewArray[] = {"任务", "项目","受试者","报告", "设置" };

			Class fragmentArray[] = { FragmentActivity0.class,
					FragmentActivity1.class,FragmentActivity2.class,FragmentActivity3.class,FragmentActivity4.class};
			initView(fragmentArray, mImageViewArray, mTextviewArray);
			
		}
		

	}

	
	
	/**
	 * 初始化组件
	 * 
	 * @param mTextviewArray
	 * @param mImageViewArray
	 */
	private void initView(Class[] fragmentArray, int[] mImageViewArray,
			String[] mTextviewArray) {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(
							getTabItemView(mImageViewArray[i],
									mTextviewArray[i]));

			// 将Tab按钮添加进Tab选项卡中
			// mTabHost.addTab(tabSpec, fragmentArray[i], null);
			mTabHost.addTab(tabSpec.setContent(new Intent(MainTabLayout.this,
					fragmentArray[i])));
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
		// if(redirect){
		// mTabHost.setCurrentTab(4);
		// }

	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int mImageViewIndex, String mTextviewArray) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewIndex);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray);

		return view;
	}

	/*    *//**
	 * 给Tab按钮设置图标和文字
	 */
	/*
	 * private View getTabItemView(int index){ View view =
	 * layoutInflater.inflate(R.layout.tab_item_view, null);
	 * 
	 * ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
	 * imageView.setImageResource(mImageViewArray[index]);
	 * 
	 * TextView textView = (TextView) view.findViewById(R.id.textview);
	 * textView.setText(mTextviewArray[index]);
	 * 
	 * return view; }
	 */
	@Override
	protected void onResume() {
		lam.dispatchResume();
		super.onResume();
		// JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		lam.dispatchPause(isFinishing());
		super.onPause();
		// JPushInterface.onPause(this);
	}

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	private EditText msgText;

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
		if (null != msgText) {
			msgText.setText(msg);
			msgText.setVisibility(android.view.View.VISIBLE);
		}
	}

}
