package jy.cn.com.yframework.simple.bar

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.AdaptScreenUtils
import jy.cn.com.ylibrary.util.BarUtils
import kotlinx.android.synthetic.main.simple_bar_activity.*

/**

 * @Author Administrator
 * @Date 2019/9/28-15:34
 * @TODO
 */
class BarSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, BarSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_bar_activity

    override fun initUI(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
        BarUtils.addMarginTopEqualStatusBarHeight(lLayout)
    }

    override fun initClassTag(): Any = BarSimpleActivity::class.java.simpleName

    override fun getResources(): Resources = AdaptScreenUtils.adaptHeight(super.getResources(), Constants.SYSTEM_DESIGN_HEIGHT)

    override fun onDestroy() {
        super.onDestroy()
        AdaptScreenUtils.closeAdapt(resources)
    }
}