package jy.cn.com.yframework.simple.thread

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.thread.ThreadCondition
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.HandlerUtil
import kotlinx.android.synthetic.main.simple_thread_activity.*

/**
 * @Author Administrator
 * @Date 2019/9/25-18:28
 * @TODO 线程测试
 */
class ThreadSimpleActivity : BaseActivity() {


    companion object {

        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ThreadSimpleActivity::class.java)
        }
    }

    var threadCondition: ThreadCondition? = null


    override fun initLayoutID(): Int = R.layout.simple_thread_activity

    override fun initUI(savedInstanceState: Bundle?) {

    }


    fun threadClick(view: View) {
        when (view.id) {
            R.id.btnStartThread -> {
                textView1.text = "启动线程--等待通知"
                btnStartThread.isEnabled = false
                btnSendThread.isEnabled = false
                startThread()
                delay()
            }
            R.id.btnSendThread -> {
                if (null == threadCondition) return
                btnSendThread.isEnabled = false
                threadCondition?.signal()
                textView1.text = "收到通知--继续执行线程"
            }
        }
    }

    private fun startThread() {
        threadCondition = ThreadCondition()
        threadCondition?.setCall {
            Thread.sleep(5000)
            HandlerUtil.runOnUiThread(Runnable {
                btnStartThread.isEnabled = true
                btnSendThread.isEnabled = true
                textView1.text = "执行线程结束"
            })
        }
        threadCondition?.start()

    }

    private fun delay() {
        HandlerUtil.runOnUiThread(Runnable { btnSendThread.isEnabled = true }, 2)
    }


}
