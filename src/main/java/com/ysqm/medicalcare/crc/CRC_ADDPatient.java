package com.ysqm.medicalcare.crc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ysqm.medicalcare.FragmentActivity0;
import com.ysqm.medicalcare.FragmentActivity2;
import com.ysqm.medicalcare.FragmentActivity3;
import com.ysqm.medicalcare.R;
import com.ysqm.medicalcare.utils.Constants;
import com.ysqm.medicalcare.utils.HttpConnections;

public class CRC_ADDPatient extends Fragment {
    SharedPreferences sp;
    String token;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        view = inflater.inflate(R.layout.crc_activity_addpatient, container, false);
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


        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        token = sp.getString("token", "");
        WebView webView = (WebView) view.findViewById(R.id.addpatient);
//		webView.loadUrl("http://101.200.235.216:8080/StockADServer/live-show.html");
//		webView.setWebViewClient(new WebViewClient(){
//	           @Override
//	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//	            // TODO Auto-generated method stub
//	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//	             view.loadUrl(url);
//	            return true;
//	        }
//	     });
        //webView.loadUrl("javascript: document.getElementById(\"account\").value=\"******\";");

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //允许JavaScript执行
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("javascript: document.body.onload=function(){window.token='"+token+"';alert(window.token);}");
        //找到Html文件，也可以用网络上的文件  
//        webView.loadUrl("http://www.baidu.com"); 
        webView.loadUrl(Constants.WSDL_URL_TRACK + Constants.CRC_ADDPATIENT + "?token=" + token);
//        webView.loadUrl("file:///android_asset/a.html");
        webView.setWebChromeClient(new WebChromeClient());
        //webView.loadUrl("javascript: window.token=\""+token+"\";");
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://103.248.103.12:8080/track/report/patientStateStatistic.html");  
//		LinearLayout tv = (LinearLayout)v.findViewById(R.id.currentordbutton);
//        tv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//FragmentActivity3.changeFragment();
//			}
//		});
        return view;


    }


}


