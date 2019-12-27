package jy.cn.com.yframework.simple.http.bean

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

/**

 * @Author Administrator
 * @Date 2019/12/27-10:34
 * @TODO
 */
class ShareInfoVo() : Parcelable {

    var title: String? = null//标题
    var targetUrl: String? = null//目标地址
    var summary: String? = null//内容
    var imageUrl: String? = null//图片URL
    var appName: String? = null//app名称
    var bitmap: Bitmap? = null//图片对象

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        targetUrl = parcel.readString()
        summary = parcel.readString()
        imageUrl = parcel.readString()
        appName = parcel.readString()
        bitmap = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(targetUrl)
        parcel.writeString(summary)
        parcel.writeString(imageUrl)
        parcel.writeString(appName)
        parcel.writeParcelable(bitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShareInfoVo> {
        override fun createFromParcel(parcel: Parcel): ShareInfoVo {
            return ShareInfoVo(parcel)
        }

        override fun newArray(size: Int): Array<ShareInfoVo?> {
            return arrayOfNulls(size)
        }
    }
}