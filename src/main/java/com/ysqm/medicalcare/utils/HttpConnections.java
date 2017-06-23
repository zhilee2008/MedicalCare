package com.ysqm.medicalcare.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpConnections {
    private static final String TYPE = "X.509";
    private final String httpsUrl = "https://www.autobotstech.com:9443/";
    private static final String PROTOCOL = "TLS";
    public String runWithHttpsUrlConnection(Context context) throws CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance(TYPE);
        InputStream in = context.getAssets().open("android.crt");
        Certificate cartificate = cf.generateCertificate(in);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setCertificateEntry("trust", cartificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        URL url = new URL(httpsUrl);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        InputStream input = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    @SuppressLint("NewApi")
    public static JSONObject httpConnectionLoginPost(String method, Map<String, String> map) {
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//		.detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
//		.penaltyLog().build());
//
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//				.detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
//				.penaltyLog().penaltyDeath().build());
        String token = "";
        String role = "";
        JSONObject obj = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.WSDL_URL + method);
            // 添加http头信息
            httppost.addHeader("Content-Type",
                    "application/json; charset=utf-8");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                obj.put(entry.getKey(), entry.getValue());
            }
            HttpEntity bodyEntity = new StringEntity(obj.toString(),
                    "utf8");
            httppost.setEntity(bodyEntity);

            HttpResponse response;
            response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            obj.put("stateCode", code);
            if (code < 400) {
                token = response.getHeaders("token")[0].getValue();
                role = response.getHeaders("role")[0].getValue();
                obj.put("token", token);
                obj.put("role", role);
                System.out.println(token);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
            try {
                obj.put("stateCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                obj.put("stateCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                obj.put("stateCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return obj;
    }


    @SuppressLint("NewApi")
    public static JSONObject httpConnectionPost(String method, String token, Map<String, String> map) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        JSONObject obj = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.WSDL_URL + method);
            // 添加http头信息
            httppost.addHeader("Content-Type",
                    "application/json; charset=utf-8");
            httppost.addHeader("token", token);

            // http post的json数据格式： {"name": "your name","parentId":
            // "id_of_parent"}
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                obj.put(entry.getKey(), entry.getValue());
            }
            //if(!("".equals(USEREMAIL))){
            HttpEntity bodyEntity = new StringEntity(obj.toString(),
                    "utf8");
            httppost.setEntity(bodyEntity);

            HttpResponse response;
            response = httpclient.execute(httppost);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //if (code == Constants.SUCCCODE) {
            String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
            obj = new JSONObject(rev);
            obj.put("statuCode", 200);
            //String status = obj.getString("status");
//				if(!("succ".equals(status))){
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//				}else{
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//			}else{
//				Toast.makeText(getApplicationContext(), "请输入邮箱",Toast.LENGTH_SHORT).show();  
//			}

        } catch (ClientProtocolException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (Exception e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return obj;
    }

    @SuppressLint("NewApi")
    public static JSONObject httpConnectionPost(String method, String token, Map<String, Object> map, String userid, String password, String name) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        JSONObject obj = new JSONObject();
        JSONObject objChild = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.WSDL_URL + method);
            // 添加http头信息
            httppost.addHeader("Content-Type",
                    "application/json; charset=utf-8");
            httppost.addHeader("token", token);

            // http post的json数据格式： {"name": "your name","parentId":
            // "id_of_parent"}
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                //obj.put(entry.getKey(), entry.getValue());
                obj.put(entry.getKey(), entry.getValue());
            }
            //if(!("".equals(USEREMAIL))){
//				HttpEntity bodyEntity = new StringEntity(obj.toString(),
//						"utf8");
            HttpEntity bodyEntity = new StringEntity("{\"user\":{\"userId\":" + userid + ",\"password\":" + password + "},\"name\":+" + name + "}",
                    "utf8");
            httppost.setEntity(bodyEntity);

            HttpResponse response;
            response = httpclient.execute(httppost);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //if (code == Constants.SUCCCODE) {
            String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
            obj = new JSONObject(rev);
            //String status = obj.getString("status");
//				if(!("succ".equals(status))){
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//				}else{
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//			}else{
//				Toast.makeText(getApplicationContext(), "请输入邮箱",Toast.LENGTH_SHORT).show();  
//			}

        } catch (ClientProtocolException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (Exception e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return obj;
    }

    @SuppressLint("NewApi")
    public static JSONObject httpConnectionPut(String method, String token, Map<String, String> map) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        JSONObject obj = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpput = new HttpPut(Constants.WSDL_URL + method);
            // 添加http头信息
            httpput.addHeader("Content-Type",
                    "application/json; charset=utf-8");
            httpput.addHeader("token", token);

            // http post的json数据格式： {"name": "your name","parentId":
            // "id_of_parent"}
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                obj.put(entry.getKey(), entry.getValue());
            }
            //if(!("".equals(USEREMAIL))){
            HttpEntity bodyEntity = new StringEntity(obj.toString(),
                    "utf8");
            httpput.setEntity(bodyEntity);

            HttpResponse response;
            response = httpclient.execute(httpput);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //if (code == Constants.SUCCCODE) {
            String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
            obj = new JSONObject(rev);
            obj.put("statuCode", code);

        } catch (ClientProtocolException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (Exception e) {
            try {
                obj.put("statuCode", 500);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return obj;
    }

    @SuppressLint("NewApi")
    public static JSONObject httpConnectionGet(String method, String token) {
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//		.detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
//		.penaltyLog().build());
//
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//				.detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
//				.penaltyLog().penaltyDeath().build());
        JSONObject obj = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(Constants.WSDL_URL + method);
            // 添加http头信息
            httpget.addHeader("Content-Type",
                    "application/json; charset=UTF-8");
            httpget.addHeader("token", token);
            httpget.addHeader("_format", "ios");
            // http post的json数据格式： {"name": "your name","parentId":
            // "id_of_parent"}

            HttpResponse response;
            response = httpclient.execute(httpget);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //if (code == Constants.SUCCCODE) {
            String rev = EntityUtils.toString(response.getEntity(), "UTF-8");// 返回json格式：
            obj = new JSONObject(rev);
            //String status = obj.getString("status");
//				if(!("succ".equals(status))){
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//				}else{
//					//Toast.makeText(getApplicationContext(), "服务器维护中请稍后",Toast.LENGTH_SHORT).show();  
//				}
//			}else{
//				Toast.makeText(getApplicationContext(), "请输入邮箱",Toast.LENGTH_SHORT).show();  
//			}

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject httpConnectionGetServer() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or  .detectAll()  for all detectable problems
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // .detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        JSONObject obj = new JSONObject();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(Constants.WSDL_SERVER_LIST);
            HttpResponse response;
            response = httpclient.execute(httpget);
            // 检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //if (code == Constants.SUCCCODE) {
            String rev = EntityUtils.toString(response.getEntity(), "UTF-8");// 返回json格式：
            obj = new JSONObject(rev);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
