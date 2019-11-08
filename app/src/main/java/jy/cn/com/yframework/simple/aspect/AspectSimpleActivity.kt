package jy.cn.com.yframework.simple.aspect

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.aspect.BehaviorTrace
import jy.cn.com.ylibrary.aspect.CheckNetwork
import jy.cn.com.ylibrary.aspect.SingleClick
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/11/8-11:55
 * @TODO
 */
class AspectSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, AspectSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_aspect_activity

    override fun initUI(savedInstanceState: Bundle?) {
    }

    override fun initClassTag(): Any = AspectSimpleActivity::class.java.simpleName

    @CheckNetwork
    fun onAspectNetwork(view: View) {
        YLogUtil.i("有网络连接")
    }

    @BehaviorTrace(name = "onClickBehavior", explain = "点击按钮")
    fun onUserBehavior(view: View) {
        YLogUtil.i("用户行为统计")
    }

    @SingleClick
    fun onSingle(view: View) {
        YLogUtil.i("点击测试")
    }
}