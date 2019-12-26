package jy.cn.com.yframework.simple.strategy

import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.strategy.MsgChain
import jy.cn.com.ylibrary.util.ActivityUtils
import kotlinx.android.synthetic.main.simple_strategy_activity.*

/**

 * @Author Administrator
 * @Date 2019/12/26-14:47
 * @TODO 消息策略
 */
class StrategySimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, StrategySimpleActivity::class.java)
        }
    }

    var msgChain = MsgChain()

    override fun initLayoutID(): Int = R.layout.simple_strategy_activity

    override fun initUI(savedInstanceState: Bundle?) {

        send_msg.setOnClickListener {
            for (i in 0..5) {
                msgChain.addChain(LevelUpMsgHandler::class.java.simpleName, i)
            }
        }

    }
}