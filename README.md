advertiser-android-sdk
======================
a)	根据用户终端收集信息（advertisingId，wifi mac）<br />
b)	调用GoCPA的Pixel传递信息以及业务参数<br />

**App开发者需传入三个参数：appId、advertiserId和referral。**<br />
**appId为长度不超过36的字符串（String）;**<br />
**advertiserId为长整型（long）；**<br />
**referral为布尔值（bool）。**<br />
这三个参数由GoCPA系统分配给app开发者。<br />

如何开始
-----------------------------------  
集成到你的项目，需要拷贝GocpaConfig.java,GocpaTracker.java和GocpaUtil.java到你的项目中<br />

在AndroidManifest.xml里面application标签内填入参数：
```xml
        <application ...
        <meta-data android:name="appId" android:value="\ app123" /> 
 		<meta-data android:name="referral" android:value="true" /> 
 		<meta-data android:name="advertiserId" android:value="\ 999" /> 
 		</application>
``` 		
其中，appId和advertiserId在需要前面加入\空格,避免数字类型解析出错

所需权限
------------------------
```xml
   <uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
``` 	
调用方法
-----------------------------------  
引入头文件
```java
import com.gocpa.android.sdk.GocpaTracker;

```
初始化GocpaTracker对象<br />
初始化之后，执行reportDevice方法即可提交用户终端设备信息。
```java
    GocpaTracker.getInstance(MainActivity.this).reportDevice();

```

修改Pixel url
------------------------------------
Pixel url由GoCPA提供，配置在GocpaConfig.java文件中，如需要，开发者可修改配置文件来修改Pixel URL。

```java
public static String PixelHost ="http://pixel.gocpa.com/Pixel";

```
