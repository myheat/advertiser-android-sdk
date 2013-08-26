package com.gocpa.android.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

public class GocpaTracker {
	private static GocpaTracker mInstance;
	private Context mContext;
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
            	 System.out.println("advertiserId:"+advertiserId);
            	 
            	 String deviceId = "meid="+meid + "&imei="+imei+"&serialId="+serialId + "&wifimac="+wifimac;
            	 try {
					deviceId = URLEncoder.encode(deviceId,"utf-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	 String httpUrl = GocpaConfig.PixelHost+"?appId="+appId+"&advertiserId="+advertiserId+"&referral="+referral+"&deviceId="+deviceId;
                 System.out.println(httpUrl);
            	 HttpGet request = new HttpGet(httpUrl);
                 HttpClient httpClient = new DefaultHttpClient();
                 try {
                     HttpResponse response = httpClient.execute(request);
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
