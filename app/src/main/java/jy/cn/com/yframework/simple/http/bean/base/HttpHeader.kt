package jy.cn.com.yframework.simple.http.bean.base

import android.os.Build
import jy.cn.com.yframework.TestApplication

class HttpHeader {
//    val channel: String = AppUtils.getChannelName()//渠道
//    val clientId: String = TestApplication.getInstance().uuid//客户端唯一ID
    val clientModel: String = android.os.Build.MODEL//机型
//    val clientSign: String = AppUtils.getSignature()//签名
//    val network: String = NetworkImpl.getNetworkTypeName()//网络类型
    val osVersion: String = Build.VERSION.RELEASE//系统OS版本
    val packager: String = TestApplication.getInstance().packageName//包名
//    val token: String = TestApplication.getInstance().token
//    val userId: String = TestApplication.getInstance().userId
//    val version: String = TestApplication.getInstance().versionCode
    val clientType = "0"//客户端类型 0 安卓，1 iOS
}