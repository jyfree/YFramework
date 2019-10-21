package jy.cn.com.yframework.simple.selector

import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.selector.selector.CompoundDrawableSelector
import jy.cn.com.ylibrary.util.ActivityUtils
import kotlinx.android.synthetic.main.simple_selector_activity.*

/**

 * @Author Administrator
 * @Date 2019/10/21-13:50
 * @TODO
 */
class SelectorSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, SelectorSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_selector_activity

    override fun initClassTag(): Any = SelectorSimpleActivity::class.java.simpleName

    override fun initUI(savedInstanceState: Bundle?) {
        btn_Shadow.setShadow(R.color.colorAccent, R.color.colorPrimary)
        btn_BgColor.setBgColor(R.color.colorAccent, R.color.colorPrimary)
        tv_TextColor.setTextColor(R.color.colorAccent, R.color.colorPrimary)
        iv_BgDrawable.setBgDrawable(R.drawable.logo_qq, R.drawable.logo_qzone)
        iv_CDrawable.setCompoundDrawable(R.drawable.logo_qq, R.drawable.logo_qzone, CompoundDrawableSelector.TOP)
    }
}