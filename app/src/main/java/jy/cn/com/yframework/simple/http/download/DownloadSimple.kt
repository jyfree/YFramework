package jy.cn.com.yframework.simple.http.download

import android.content.Context
import android.content.Intent
import jy.cn.com.yframework.TestApplication
import jy.cn.com.ylibrary.BaseConstants
import jy.cn.com.ylibrary.db.DownloadDao
import jy.cn.com.ylibrary.http.bean.DownInfo
import jy.cn.com.ylibrary.http.download.local.HttpDownManager
import jy.cn.com.ylibrary.http.download.local.listener.HttpDownOnNextListener
import jy.cn.com.ylibrary.http.download.sys.DownloadServer
import jy.cn.com.ylibrary.util.AppUtils
import jy.cn.com.ylibrary.util.FileUtils
import jy.cn.com.ylibrary.util.YLogUtil
import java.io.File

/**

 * @Author Administrator
 * @Date 2019/9/27-15:55
 * @TODO 下载示例 ，注意：需要有访问sdcard权限
 */
object DownloadSimple {


    /**
     * 系统下载方式： DownloadManager
     */
    fun startSysDownload(context: Context?, url: String) {
        val intent = Intent(context, DownloadServer::class.java)
        intent.putExtra(BaseConstants.ActionKey.ACTION_KEY_URL, url)
        context?.startService(intent)
    }

    /**
     * 自定义下载方式
     */
    fun startLocalDownload(url: String) {
        var downInfo = DownloadDao.queryDownloadInfoByPath(url)
        if (downInfo == null) {
            downInfo = DownInfo(url)
            downInfo.updateProgress = true
            downInfo.savePath = FileUtils.getSdcardPath()
            DownloadDao.insertOrUpdate(downInfo)
        }

        val listener = object : HttpDownOnNextListener<DownInfo>() {
            override fun onNext(t: DownInfo) {

            }

            override fun onStart() {

            }

            override fun onComplete() {
                val fileName = url.substring(url.lastIndexOf("/") + 1)
                val file = File(FileUtils.getSdcardPath() + fileName)
                AppUtils.installApp(file, "${TestApplication.getInstance()?.packageName}.FileProvider")
            }

            override fun updateProgress(readLength: Long, countLength: Long) {
                val progress = ((readLength.toFloat() / countLength.toFloat()) * 100).toInt()
                YLogUtil.i("updateProgress", "$progress%")
            }

        }
        downInfo.listener = listener
        HttpDownManager.startDownload(downInfo)
    }
}