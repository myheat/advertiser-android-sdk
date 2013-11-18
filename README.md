advertiser-android-sdk
======================
a)	SDK for collecting devices' infomation（advertisingId，wifi mac）<br />
b)	send infomation to GoCPA<br />

**Developers need three arguments：appId、advertiserId and referral。 you can apply from GoCPA**<br />
**appId String type, and length less than 32 （String）;**<br />
**advertiserId long type（long）；**<br />
**referral bool type（bool）。**<br />
GoCPA distribute to app developers<br />

How to start
-----------------------------------  
copy these files following<br />
GocpaConfig.java<br />
GocpaTracker.java<br />
GocpaUtil.java<br />
PersistentCookieStore.java<br />
into your project<br />

modify AndroidManifest.xml file in application tag：
```xml
<application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        
        <meta-data android:name="appId" android:value="\ app123" /> 
 	<meta-data android:name="referral" android:value="true" /> 
 	<meta-data android:name="advertiserId" android:value="\ 999" /> 
 	
 </application>
``` 		
important: appId and advertiserId need \ and Blank Character in front,to tell system it's a number.

android Permission
------------------------
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
``` 	

ReportDevice
-----------------------------------  
import sdk class on top of your class

```java
import com.gocpa.android.sdk.GocpaTracker;

```
init GocpaTracker object <br />
after that, call reportDevice method to sumbit device's infomation.
```java
    GocpaTracker.getInstance(MainActivity.this).reportDevice();

```

ReportEvent
----------------------------------- 
monitoring users' behaviors（passing a game level、buying acting etc.）<br />
reportEvent(String event)<br />
reportEvent(String event,float amount,String currency)<br />
<br />
```java

GocpaTracker.getInstance(MainActivity.this).reportEvent("event_name");

```


Pixel url
------------------------------------
Pixel url assigned by GoCPA, you can modify it in GocpaConfig.java if needed.
```java
public static String PixelHost ="http://pixel.gocpa.com/Pixel";

```
