package jy.cn.com.yframework.simple.http

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.download.DownloadSimple
import jy.cn.com.ylibrary.acp.Acp
import jy.cn.com.ylibrary.acp.AcpListener
import jy.cn.com.ylibrary.acp.AcpOptions
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.ToastUtil
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/27-16:06
 * @TODO 下载示例
 */
class DownloadSimpleActivity : BaseActivity() {

    private val url = "https://cdn.9mitao.com/apk/android/9mitao_4.3.8.apk"

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, DownloadSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_download_activity

    override fun initUI(savedInstanceState: Bundle?) {

    }


    fun onDownload(view: View) {
        when (view.id) {
            R.id.sys_download -> checkDownload(true)
            R.id.local_download -> checkDownload(false)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun checkDownload(isSys: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canInstallApp = packageManager.canRequestPackageInstalls()
            YLogUtil.i("canInstallApp:", canInstallApp)
        }
        Acp.getInstance().acpManager
                .setAcPermissionOptions(AcpOptions.beginBuilder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).build())
                .setAcPermissionListener(object : AcpListener {
                    override fun onDenied(permissions: MutableList<String>?) {
                        YLogUtil.e("权限申请--拒绝", permissions?.toString())
                        ToastUtil.showToast(this@DownloadSimpleActivity, "权限申请--拒绝" + permissions?.toString())
                    }

                    override fun onGranted() {
                        YLogUtil.i("权限申请--同意")
                        if (isSys) {
                            DownloadSimple.startSysDownload(this@DownloadSimpleActivity, url)
                        } else {
                            DownloadSimple.startLocalDownload(url)
                        }
                    }
                })
                .request(this)
    }
}