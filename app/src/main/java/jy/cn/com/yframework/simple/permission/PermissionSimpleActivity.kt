package jy.cn.com.yframework.simple.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.telephony.TelephonyManager
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.acp.Acp
import jy.cn.com.ylibrary.acp.AcpListener
import jy.cn.com.ylibrary.acp.AcpOptions
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.ToastUtil
import jy.cn.com.ylibrary.util.YLogUtil
import java.io.File


/**

 * @Author Administrator
 * @Date 2019/10/12-14:49
 * @TODO
 */
class PermissionSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, PermissionSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_permission_activity

    override fun initClassTag(): Any = PermissionSimpleActivity::class.java.simpleName

    override fun initUI(savedInstanceState: Bundle?) {

    }

    fun onClickPermission(view: View) {
        when (view.id) {
            R.id.get_sdcard -> requestSdcard()
            R.id.get_imei -> requestReadPhone()
            R.id.get_all -> requestAll()
        }
    }

    private fun requestAll() {
        Acp.getInstance().acpManager
                .setShowRational(true)
                .setAcPermissionOptions(AcpOptions.beginBuilder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE).build())
                .setAcPermissionListener(object : AcpListener {
                    override fun onDenied(permissions: MutableList<String>?) {
                        YLogUtil.e("权限申请--拒绝", permissions?.toString())
                        ToastUtil.showToast(this@PermissionSimpleActivity, "权限申请--拒绝" + permissions?.toString())

                    }

                    override fun onGranted() {
                        YLogUtil.i("权限申请--同意")
                        getAll()
                    }
                })
                .request(this)
    }

    private fun requestSdcard() {
        Acp.getInstance().acpManager
                .setShowRational(false)
                .setAcPermissionOptions(AcpOptions.beginBuilder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).build())
                .setAcPermissionListener(object : AcpListener {
                    override fun onDenied(permissions: MutableList<String>?) {
                        YLogUtil.e("权限申请--拒绝", permissions?.toString())
                        ToastUtil.showToast(this@PermissionSimpleActivity, "权限申请--拒绝" + permissions?.toString())
                    }

                    override fun onGranted() {
                        YLogUtil.i("权限申请--同意")
                        writeSD()
                    }
                })
                .request(this)
    }

    private fun requestReadPhone() {
        Acp.getInstance().acpManager
                .setShowRational(true)
                .setAcPermissionOptions(AcpOptions.beginBuilder().setPermissions(Manifest.permission.READ_PHONE_STATE).build())
                .setAcPermissionListener(object : AcpListener {
                    override fun onDenied(permissions: MutableList<String>?) {
                        YLogUtil.e("权限申请--拒绝", permissions?.toString())
                        ToastUtil.showToast(this@PermissionSimpleActivity, "权限申请--拒绝" + permissions?.toString())
                    }

                    override fun onGranted() {
                        YLogUtil.i("权限申请--同意")
                        getIMEI()
                    }
                })
                .request(this)
    }


    @SuppressLint("MissingPermission", "HardwareIds")
    private fun getAll() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        val acpDir = getCacheDir("acp", this)
        ToastUtil.showToast(this, "读imei成功：" + tm?.deviceId + "\n写SD成功：" + acpDir)
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun getIMEI() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        tm?.let { ToastUtil.showToast(this, "读imei成功：" + it.deviceId) }
    }

    private fun writeSD() {
        val acpDir = getCacheDir("acp", this)
        acpDir?.let { ToastUtil.showToast(this, "写SD成功：" + acpDir.absolutePath) }

    }

    private fun getCacheDir(dirName: String, context: Context): File? {
        val result: File
        result = if (existsSdcard() == true) {
            val cacheDir = context.externalCacheDir
            if (cacheDir == null) {
                File(Environment.getExternalStorageDirectory(), context.packageName + "/cache/" + dirName)
            } else {
                File(cacheDir, dirName)
            }
        } else {
            File(context.cacheDir, dirName)
        }
        return if (result.exists() || result.mkdirs()) {
            result
        } else {
            null
        }
    }

    private fun existsSdcard(): Boolean? {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}