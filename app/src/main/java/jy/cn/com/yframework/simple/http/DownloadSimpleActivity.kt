package jy.cn.com.yframework.simple.http

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.download.DownloadSimple
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils

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

    override fun initClassTag(): Any = DownloadSimpleActivity::class.java.simpleName

    override fun initUI(savedInstanceState: Bundle?) {

    }


    fun onDownload(view: View) {
        when (view.id) {
            R.id.sys_download -> DownloadSimple.startSysDownload(this, url)
            R.id.local_download -> DownloadSimple.startLocalDownload(url)
        }
    }
}