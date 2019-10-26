package jy.cn.com.yframework.simple.db

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseAppCompatActivity
import jy.cn.com.ylibrary.coroutine.CoroutineResultCallback
import jy.cn.com.ylibrary.db.DownloadDao
import jy.cn.com.ylibrary.thread.lifecycle.ThreadResultCallback
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/10/24-18:30
 * @TODO
 */
class DBSimpleActivity : BaseAppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, DBSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_db_activity

    override fun initClassTag(): Any = DBSimpleActivity::class.java.simpleName

    override fun initUI(savedInstanceState: Bundle?) {

    }

    fun onLoadDB(view: View) {
        when (view.id) {
            //协程方式查询db
            R.id.load_coroutine -> requestCoroutine()
            //主线程查询db
            R.id.load_def -> requestDef()
            //子线程查询db
            R.id.load_thread -> requestThread()
        }
    }

    private fun requestCoroutine() {
        val startTime = System.currentTimeMillis()
        for (i in 0..10000) {
            DownloadDao.getListInfoToCoroutine(CoroutineResultCallback {
                YLogUtil.i("协程--data", it, i, Thread.currentThread().name)
            }, this)
        }
        YLogUtil.i("协程--全部--time", System.currentTimeMillis() - startTime)
    }

    private fun requestDef() {
        val startTime = System.currentTimeMillis()
        for (i in 0..1000) {
            val itemStartTime = System.currentTimeMillis()
            DownloadDao.getListInfo()
            YLogUtil.i("主线程--单次--time", System.currentTimeMillis() - itemStartTime)
        }
        YLogUtil.i("主线程--全部--time", System.currentTimeMillis() - startTime)
    }

    private fun requestThread() {
        val startTime = System.currentTimeMillis()
        for (i in 0..10000) {
            DownloadDao.getListInfoToThread(ThreadResultCallback {
                YLogUtil.i("子线程--data", it, i, Thread.currentThread().name)
            }, this)
        }
        YLogUtil.i("子线程--全部--time", System.currentTimeMillis() - startTime)
    }
}