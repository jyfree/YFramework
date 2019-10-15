package jy.cn.com.yframework

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import jy.cn.com.yframework.simple.bar.BarSimpleActivity
import jy.cn.com.yframework.simple.cmd.CMDSimpleActivity
import jy.cn.com.yframework.simple.dialog.DialogSimple
import jy.cn.com.yframework.simple.http.ApiSimpleActivity
import jy.cn.com.yframework.simple.http.DownloadSimpleActivity
import jy.cn.com.yframework.simple.http.MvpApiSimpleActivity
import jy.cn.com.yframework.simple.http.bean.SendGiftVo
import jy.cn.com.yframework.simple.loading.LoadingSimpleActivity
import jy.cn.com.yframework.simple.permission.PermissionSimpleActivity
import jy.cn.com.yframework.simple.rxbus.RxBusSimpleActivity
import jy.cn.com.yframework.simple.social.LoginSimpleActivity
import jy.cn.com.yframework.simple.social.PaySimpleActivity
import jy.cn.com.yframework.simple.social.ShareSimpleActivity
import jy.cn.com.yframework.simple.sp.SpSimpleActivity
import jy.cn.com.yframework.simple.thread.ThreadSimpleActivity
import jy.cn.com.yframework.simple.timer.TimerSimpleActivity
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.cmd.CMDMessage
import jy.cn.com.ylibrary.cmd.CMDReceiverListener
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private var receiverListener: CMDReceiverListener? = null

    override fun initLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initUI(savedInstanceState: Bundle?) {
        receiverListener = CMDReceiverListener()
        startTimer()
    }

    override fun initClassTag(): Any {
        return MainActivity::class.java.simpleName
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    fun go2Act(v: View) {
        when (v.id) {
            R.id.go_RxBus -> RxBusSimpleActivity.startAct(this)
            R.id.go_Thread -> ThreadSimpleActivity.startAct(this)
            R.id.go_Loading -> LoadingSimpleActivity.startAct(this)
            R.id.go_api -> ApiSimpleActivity.startAct(this)
            R.id.go_mvp_api -> MvpApiSimpleActivity.startAct(this)
            R.id.go_download -> DownloadSimpleActivity.startAct(this)
            R.id.showDialog -> DialogSimple(this, R.style.Theme_AppCompat_Dialog).show()
            R.id.go_cmd -> CMDSimpleActivity.startAct(this)
            R.id.go_bar -> BarSimpleActivity.startAct(this)
            R.id.go_share -> ShareSimpleActivity.startAct(this)
            R.id.go_login -> LoginSimpleActivity.startAct(this)
            R.id.go_pay -> PaySimpleActivity.startAct(this)
            R.id.go_timer -> TimerSimpleActivity.startAct(this)
            R.id.go_permission -> PermissionSimpleActivity.startAct(this)
            R.id.go_sp -> SpSimpleActivity.startAct(this)
        }
    }


    //********************************定时发送cmd消息********************************************
    private var disposable: Disposable? = null

    private fun startTimer() {
        if (disposable != null) return
        disposable = Observable.interval(10, 20, TimeUnit.SECONDS)
                .subscribe {
                    val msg2 = CMDMessage(Constants.CmdType.HOME, SendGiftVo("123", "456", 1, 2, 3))
                    val msg3 = CMDMessage(Constants.CmdType.ME, 3)
                    receiverListener?.onReceivedCMDMessage(msg2)
                    receiverListener?.onReceivedCMDMessage(msg3)
                }
    }

    private fun stopTimer() {
        disposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
        disposable = null
    }

}
