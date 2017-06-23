package com.ysqm.medicalcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	LoginThread lth;
	private String userId;
	private String userPWD;
	SharedPreferences sp;
	String token;
	private ProgressDialog pd;
	LinearLayout view;
	private Spinner spinner;
	private Map data_map;
	private ArrayAdapter<Map> arr_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		token = sp.getString("token", "");
		userId = sp.getString("userId", "");
		userPWD = sp.getString("userPWD", "");

		/*Constants.HOST = sp.getString("host", "");
		Constants.PORT = sp.getString("port", "");*/

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		view = (LinearLayout) findViewById(R.id.login_form);

/*		spinner = (Spinner) findViewById(R.id.spinner);
		List<SpinnerData> lst = new ArrayList<SpinnerData>();

		HttpConnections httpConnections = new HttpConnections();
		JSONObject obj = httpConnections.httpConnectionGetServer();

		try {
			JSONArray data = obj.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				SpinnerData c = new SpinnerData(data.getJSONObject(i)
						.getString("name"), data.getJSONObject(i).getString(
						"host"), data.getJSONObject(i).getString("port"));

				lst.add(c);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<SpinnerData> Adapter = new ArrayAdapter<SpinnerData>(this,
				android.R.layout.simple_spinner_item, lst);

		Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(Adapter);
		spinner.setSelection(0, true);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SpinnerData data = (SpinnerData) spinner
						.getItemAtPosition(position);
				Toast.makeText(LoginActivity.this, data.getName(),
						Toast.LENGTH_SHORT).show();
				Constants.HOST = data.getHost();
				Constants.PORT = data.getPort();
				Editor editor = sp.edit();
				editor.putString("host", Constants.HOST);
				editor.putString("port", Constants.PORT);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});*/

		pd = ProgressDialog.show(LoginActivity.this, "提示", "加载中，请稍后……");

		if (!("".equals(token))) {
			view.setVisibility(View.INVISIBLE);
			if (!("".equals(userId)) && !("".equals(userPWD))) {
				lth = new LoginThread();
				lth.start();
			}
		} else {
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						pd = ProgressDialog.show(LoginActivity.this, "提示",
								"加载中，请稍后……");
						EditText userIdView = (EditText) findViewById(R.id.userid);
						userId = userIdView.getText().toString();
						EditText userPWDView = (EditText) findViewById(R.id.userpwd);
						
						
						
/*						SpinnerData data = (SpinnerData) spinner
								.getItemAtPosition(0);
						Constants.HOST = data.getHost();
						Constants.PORT = data.getPort();
						 
						if("".equals(Constants.HOST)||"".equals(Constants.PORT)){
							Toast.makeText(LoginActivity.this, "请选择服务器",
									 5).show();
							pd.dismiss();
							return;
						}*/
						userPWD = userPWDView.getText().toString();
						
						// login
						if ("".equals(userId) || "".equals(userPWD)) {
							Toast.makeText(LoginActivity.this, "请输入正确的用户名和密码",
									5).show();
							pd.dismiss();
							
							
						} else {
							lth = new LoginThread();
							lth.start();
						}

					}
				});
	}

	class SpinnerData {

		private String name = "";
		private String port = "";
		private String host = "";

		public SpinnerData() {
			name = "";
			port = "";
			host = "";
		}

		public SpinnerData(String _name, String _port, String _host) {
			name = _name;
			port = _port;
			host = _host;

		}

		@Override
		public String toString() {

			return name;
		}

		public String getName() {
			return name;
		}

		public String getPort() {
			return port;
		}

		public String getHost() {
			return host;
		}
	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			pd.dismiss();
			if (Integer.parseInt(msg.getData().getString("stateCode")
					.toString()) < 400) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(LoginActivity.this, MainTabLayout.class);
				bundle.putSerializable("role", msg.getData().getString("role"));
				intent.putExtras(bundle);
				LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
			} else {
				view.setVisibility(View.VISIBLE);
				Toast.makeText(LoginActivity.this, "用户登录失败请重新登录", 5).show();
			}

			super.handleMessage(msg);

		}
	};

	class LoginThread extends Thread {
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			HttpConnections httpConnections = new HttpConnections();

			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("userId", userId);
			mapParam.put("password", userPWD);
			// mapParam.put("userId", "crc2");
			// mapParam.put("password", "passw0rd");
			// mapParam.put("userId", "patient2");
			// mapParam.put("password", "passw0rd");
			// mapParam.put("userId", "cra1");
			// mapParam.put("password", "passw0rd");
			JSONObject obj = httpConnections.httpConnectionLoginPost(
					Constants.LOGIN, mapParam);
			try {
				if (Integer.parseInt(obj.get("stateCode").toString()) < 400) {
					Editor editor = sp.edit();
					editor.putString("token", obj.getString("token"));
					editor.putString("userType", obj.getString("role"));
					editor.putString("userId", obj.getString("userId"));
					editor.putString("userPWD", obj.getString("password"));

					editor.commit();

					Bundle bundle = new Bundle();
					bundle.putString("stateCode", obj.getString("stateCode"));
					bundle.putString("role", obj.getString("role"));
					JPushInterface.setAliasAndTags(getApplicationContext(),
							obj.getString("userId"), null, null);
					msg.setData(bundle);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("stateCode", obj.getString("stateCode"));
					msg.setData(bundle);
					// Toast.makeText(LoginActivity.this, "请输入正确的用户名和密码",
					// 5).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LoginActivity.this.myHandler.sendMessage(msg);
		}
	}

}
