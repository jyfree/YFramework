# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-soft\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

#***********************************默认基本混淆****************************************
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-dontwarn
#指定代码的压缩级别
-optimizationpasses 5
#是否使用大小写混合
-dontusemixedcaseclassnames
#是否混淆第三方jar
-dontskipnonpubliclibraryclasses
#指定不去忽略包可见的库类的成员。
-dontskipnonpubliclibraryclassmembers
#不优化输入的类文件
#-dontoptimize
#预校验
-dontpreverify
#混淆时是否记录日志
-verbose
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/,!class/merging/
#保护注解
-keepattributes Annotation
-keepattributes *JavascriptInterface*

#google默认 不混淆
-keep class android.** {*; }
-keep public class * extends android.view
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.backup.BackupAgentHelper
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers class * extends android.support.v7.app.AppCompatActivity {
    public void *(android.view.View);
}

#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
}

#如果引用了v4或者v7包
-dontwarn android.support.**
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

#***********************************默认基本混淆  end****************************************

#***********************************ylibrary混淆****************************************
#Rxbus混淆
-dontwarn jy.cn.com.ylibrary.rxbus.**
-keep class jy.cn.com.ylibrary.rxbus.** { *;}
-keepattributes *Annotation
-keep @jy.cn.com.ylibrary.rxbus.Subscribe class * {*;}
-keep class * {
    @jy.cn.com.ylibrary.rxbus.Subscribe <fields>;
}
-keepclassmembers class * {
    @jy.cn.com.ylibrary.rxbus.Subscribe <methods>;
}
#Rxbus混淆end

#反射机制
-keep class jy.cn.com.ylibrary.util.ObjectUtils
-keepclassmembers class jy.cn.com.ylibrary.util.ObjectUtils {
  public *;
}

#cmd拦截器
-dontwarn jy.cn.com.ylibrary.cmd.**
-keep class jy.cn.com.ylibrary.cmd.** { *;}
-keepattributes *Annotation
-keep @jy.cn.com.ylibrary.cmd.CMD class * {*;}
-keep class * {
    @jy.cn.com.ylibrary.cmd.CMD <fields>;
}
-keepclassmembers class * {
    @jy.cn.com.ylibrary.cmd.CMD <methods>;
}
#cmd拦截器

#***********************************ylibrary混淆  end****************************************


#********************************第三方混淆*************************************
#微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
#微信end

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
-renamesourcefileattribute SourceFile
-keep class android.net.http.SslError
-keep class android.webkit.** {*;}
-keep class com.alipay.sdk.** {*; }
#支付宝end


#qq分享
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-dontwarn com.baidu.**
-dontwarn com.tencent.**
#qq分享 end

#微信分享
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage { *;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#微信分享 end


#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#okhttp end

# http client
-keep class android.net.http.SslError
-keep class android.webkit.** {*;}
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.**


# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent
# RxJava RxAndroid end

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
# Okio end

# Retrofit2
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
# Retrofit2 end

# 微博
-keep class com.sina.weibo.sdk.** { *; }
# 微博 end



#glide#
#glide4.0
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# 从glide4.0开始，GifDrawable没有提供getDecoder()方法，
# 需要通过反射获取gifDecoder字段值，所以需要保持GifFrameLoader和GifState类不被混淆
-keep class com.bumptech.glide.load.resource.gif.GifDrawable$GifState{*;}
-keep class com.bumptech.glide.load.resource.gif.GifFrameLoader {*;}
#glide end#

#********************************第三方混淆*************************************



#********************************APP混淆*************************************

#不混淆资源类
-keep public class [jy.cn.com.yframework].R$*{
    public static final int *;
}
#一般model最好避免混淆
-keep class jy.cn.com.yframework.simple.http.bean.** { *; }
-keep class jy.cn.com.ylibrary.http.bean.** { *; }
-keep class jy.cn.com.socialsdklibrary.wx.WXPayBean
#********************************APP混淆 end*************************************
