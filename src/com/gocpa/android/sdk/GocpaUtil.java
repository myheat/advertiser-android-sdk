package com.gocpa.android.sdk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class GocpaUtil {
	static final boolean PRINT_LOG = true;
	
	public static boolean checkPermissionGranted(String p, Context c) {
		return c.checkCallingOrSelfPermission(p) == PackageManager.PERMISSION_GRANTED;
	}

	public static String getAndroidID(Context context) {
		String id = Secure.getString(context.getApplicationContext()
				.getContentResolver(), Secure.ANDROID_ID);
		if (id == null) {
			id = "";
		} 
		return id;
	}
	public static String getAndroidBrand() {
		return android.os.Build.BRAND;
		
	}
	public static String getAndroidModel() {
		return android.os.Build.MODEL;
		
	}
	public static String getAndroidVersion() {
		return android.os.Build.VERSION.RELEASE;
		
	}
	public static String loadFileAsString(String filePath){
		StringBuffer fileData = new StringBuffer();
		try{
		    
		    BufferedReader reader = new BufferedReader(new FileReader(filePath));
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	fileData.append(line).append("\n");
		    }
		    return fileData.toString();
		    
		   
		}catch(Exception e){
			
		}
	    return fileData.toString();
	}
	public static String getIMEI(Context context) {
        TelephonyManager manager = 
            (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM){
    	   return manager.getDeviceId();
       }else{
    	   return "";
       }
    }
	
	public static String getOperator(Context context) {
        TelephonyManager manager = 
            (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       
        String operator = manager.getSimOperator();
        if(operator!=null){ 
        	if(operator.equals("46000") || operator.equals("46002")|| operator.equals("46007")){
        		return "ChinaMobile";
        	}else if(operator.equals("46001")){
        		return "ChinaUnicom";
        	}else if(operator.equals("46003")){
        		return "ChinaTelecom";
        	} 
       }
       return "Unknown";
        
    }
	
	public static String getMEID(Context context) {
        TelephonyManager manager = 
            (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
       
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
     	   return manager.getDeviceId();
        }else{
     	   return "";
        }
    }
	
	public static String getMacAddress(Context context) {
		String mac = null;
		
		if (GocpaUtil.checkPermissionGranted(
				android.Manifest.permission.ACCESS_WIFI_STATE, context)) {
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			mac = wm.getConnectionInfo().getMacAddress();
		}
		if (mac == null) {
			mac = loadFileAsString("/sys/class/net/eth0/address")
            .toUpperCase().substring(0, 17);
		} 

		return mac;
	}
	
	
	
	public static String getAppId(final Context context) {
		String appId = null;

		PackageManager packageManager = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = packageManager
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			appId = applicationInfo.metaData
					.getString("appId");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (appId == null) {
			//log error

		}

		return appId;
	}
	public static boolean isReferral(final Context context) {
		boolean referral = false;

		PackageManager packageManager = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = packageManager
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			referral = applicationInfo.metaData
					.getBoolean("referral");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return referral;
	}
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						if (inetAddress instanceof Inet4Address) {
							return inetAddress.getHostAddress().toString();
						}
					}
				}
			}
		} catch (SocketException e) {
			
			e.printStackTrace();
		}

		
		return "";
	}

	public static String getAdvertiserId(final Context context) {
		String advertiserId = null;

		PackageManager packageManager = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = packageManager
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			advertiserId = applicationInfo.metaData
					.getString("advertiserId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if (advertiserId == null) {
			//log error

		}

		return advertiserId;
	}


}
