package jy.cn.com.yframework.simple.http.bean

import android.os.Parcel
import android.os.Parcelable
import jy.cn.com.ylibrary.http.bean.SingleBaseBean

/**

 * @Author Administrator
 * @Date 2019/1/10-19:42
 * @TODO 微信支付
 */
class WXPayVo() : Parcelable, SingleBaseBean() {
    var payId: Long? = null//支付订单号，可用于主动查询
    var appId: String? = null//appId
    var partnerId: String? = null//商户ID
    var prepayId: String? = null//微信订单ID
    var packageValue: String? = null//签名包
    var nonceStr: String? = null//随机字符串
    var timeStamp: String? = null//时间戳
    var sign: String? = null//	签名

    constructor(parcel: Parcel) : this() {
        payId = parcel.readValue(Long::class.java.classLoader) as? Long
        appId = parcel.readString()
        partnerId = parcel.readString()
        prepayId = parcel.readString()
        packageValue = parcel.readString()
        nonceStr = parcel.readString()
        timeStamp = parcel.readString()
        sign = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(payId)
        parcel.writeString(appId)
        parcel.writeString(partnerId)
        parcel.writeString(prepayId)
        parcel.writeString(packageValue)
        parcel.writeString(nonceStr)
        parcel.writeString(timeStamp)
        parcel.writeString(sign)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WXPayVo> {
        override fun createFromParcel(parcel: Parcel): WXPayVo {
            return WXPayVo(parcel)
        }

        override fun newArray(size: Int): Array<WXPayVo?> {
            return arrayOfNulls(size)
        }
    }
}