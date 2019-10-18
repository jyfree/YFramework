package jy.cn.com.ylibrary.helper

import android.Manifest
import android.content.Context
import jy.cn.com.ylibrary.acp.Acp
import jy.cn.com.ylibrary.acp.AcpListener
import jy.cn.com.ylibrary.acp.AcpOptions
import jy.cn.com.ylibrary.pic.Pic
import jy.cn.com.ylibrary.pic.PicListener
import jy.cn.com.ylibrary.pic.PicOptions
import jy.cn.com.ylibrary.util.ToastUtil
import jy.cn.com.ylibrary.util.YLogUtil

/**
 * @Author Administrator
 * @Date 2019/10/18-15:03
 * @TODO
 */
object PicHelper {

    const val TAKE_CAMERA = 1//拍照
    const val TAKE_PICTURE = 2//相册

    fun takePic(context: Context, type: Int, picOptions: PicOptions, listener: PicListener) {
        Acp.getInstance().acpManager
                .setShowRational(false)
                .setAcPermissionOptions(AcpOptions.beginBuilder().setPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA).build())
                .setAcPermissionListener(object : AcpListener {
                    override fun onDenied(permissions: MutableList<String>?) {
                        YLogUtil.e("权限申请--拒绝", permissions?.toString())
                        ToastUtil.showToast(context, "权限申请--拒绝" + permissions?.toString())
                    }

                    override fun onGranted() {
                        YLogUtil.i("权限申请--同意")
                        if (type == TAKE_CAMERA) {
                            Pic.getInstance().picManager.setPicOptions(picOptions).setListener(listener).takeCamera(context)

                        } else if (type == TAKE_PICTURE) {
                            Pic.getInstance().picManager.setPicOptions(picOptions).setListener(listener).takePhotoAlbum(context)
                        }
                    }
                })
                .request(context)

    }

}
