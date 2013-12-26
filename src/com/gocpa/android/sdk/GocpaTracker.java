package com.gocpa.android.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class GocpaTracker {
	private static GocpaTracker mInstance;
	private Context mContext;
	private static HttpClient httpClient;
	private GocpaTracker(){
        BasicHttpParams params = new BasicHttpParams();
    	SchemeRegistry schemeRegistry = new SchemeRegistry();
    	schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    	ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
    	httpClient = new DefaultHttpClient(cm,params);
	}
	public static GocpaTracker getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = new GocpaTracker();
        }
        mInstance.startNewSession(context);
        
    	
        return mInstance;
    }
	public void startNewSession(final Context context) {
        mContext = context.getApplicationContext();

    }

	public void reportEvent(String event){
		reportEvent(event,0,"");
	}
	public void reportEvent(final String event,final float amount,final String currency){
		new Thread(new Runnable() {
            @Override
			public void run() {
            	 String appId = GocpaUtil.getAppId(mContext);
            	 String advertiserId = GocpaUtil.getAdvertiserId(mContext);
            	 boolean referral = GocpaUtil.isReferral(mContext);
            	 String deviceBrand = GocpaUtil.getAndroidBrand();
            	 String deviceModel = GocpaUtil.getAndroidModel();
            	 String OSVersion = GocpaUtil.getAndroidVersion();
            	 String Operator = GocpaUtil.getOperator(mContext);
            	 
            	 String wifimac = GocpaUtil.getMacAddress(mContext);
            	 String serialId = GocpaUtil.getAndroidID(mContext);
            	 String imei = GocpaUtil.getIMEI(mContext);
            	 String meid = GocpaUtil.getMEID(mContext);
            	 String event_encoded =event;
            	 String deviceId = "meid="+meid + "&imei="+imei+"&serialId="+serialId + "&wifimac="+wifimac
            	 	+"&deviceBrand="+deviceBrand + "&deviceModel="+deviceModel + "&OSVersion="+OSVersion
            	 	+"&Operator="+Operator;
            	 try {
            		 appId = URLEncoder.encode(appId,"utf-8");
            		 advertiserId = URLEncoder.encode(advertiserId,"utf-8");
            		 event_encoded = URLEncoder.encode(event,"utf-8");
            		 
					deviceId = URLEncoder.encode(deviceId,"utf-8");
					
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	 String httpUrl = GocpaConfig.PixelHost+"?appId="+appId+"&advertiserId="+advertiserId+"&referral="
            	 +referral+"&deviceId="+deviceId+"&event="+event_encoded + "&amount="+amount + "&currency="+currency;
            	 HttpGet request = new HttpGet(httpUrl);
                 
                 
            	 CookieStore cookieStore = new PersistentCookieStore(mContext);
                 // Create local HTTP context
                 HttpContext localContext = new BasicHttpContext();
                 // Bind custom cookie store to the local context
                 localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                 /*List<Cookie> cookies = cookieStore.getCookies();
            	 for (int i = 0; i < cookies.size(); i++) {
                     System.out.println("Local cookie: " + cookies.get(i));
                 }*/
            	 //cookieStore.clear();
                 try {
                     HttpResponse response = httpClient.execute(request,localContext);
                     if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                    	 
                         //String str = EntityUtils.toString(response.getEntity());
                         //System.out.println("response:"+str);
                         
                     }else{
                     }
                 } catch (ClientProtocolException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();    
                 } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
            	 
            }
		}, "GocpaTrackerThread").start();
		
	}
	public void reportDevice(){
		new Thread(new Runnable() {
            @Override
			public void run() {
            	 String appId = GocpaUtil.getAppId(mContext);
            	 String advertiserId = GocpaUtil.getAdvertiserId(mContext);
            	 boolean referral = GocpaUtil.isReferral(mContext);
            	 String wifimac = GocpaUtil.getMacAddress(mContext);
            	 String serialId = GocpaUtil.getAndroidID(mContext);
            	 String imei = GocpaUtil.getIMEI(mContext);
            	 String meid = GocpaUtil.getMEID(mContext);
            	 String deviceBrand = GocpaUtil.getAndroidBrand();
            	 String deviceModel = GocpaUtil.getAndroidModel();
            	 String OSVersion = GocpaUtil.getAndroidVersion();
            	 String Operator = GocpaUtil.getOperator(mContext);
            	 
            	 String deviceId = "meid="+meid + "&imei="+imei+"&serialId="+serialId + "&wifimac="+wifimac
         	 	+"&deviceBrand="+deviceBrand + "&deviceModel="+deviceModel + "&OSVersion="+OSVersion
         	 	+"&Operator="+Operator;
            	 
            	 try {
            		 appId = URLEncoder.encode(appId,"utf-8");
            		 advertiserId = URLEncoder.encode(advertiserId,"utf-8");
					deviceId = URLEncoder.encode(deviceId,"utf-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	 String httpUrl = GocpaConfig.PixelHost+"?appId="+appId+"&advertiserId="+advertiserId+"&referral="+referral+"&deviceId="+deviceId;
                 //System.out.println(httpUrl);
            	 HttpGet request = new HttpGet(httpUrl);
                 //HttpClient httpClient = new DefaultHttpClient();
            	 CookieStore cookieStore = new PersistentCookieStore(mContext);
                 // Create local HTTP context
                 HttpContext localContext = new BasicHttpContext();
                 // Bind custom cookie store to the local context
                 localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                 try {
                     HttpResponse response = httpClient.execute(request,localContext);
                     if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                         //String str = EntityUtils.toString(response.getEntity());
                         
                     }else{
                     }
                 } catch (ClientProtocolException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();    
                 } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
            	 
            }
		}, "GocpaTrackerThread").start();
		
	}
}
