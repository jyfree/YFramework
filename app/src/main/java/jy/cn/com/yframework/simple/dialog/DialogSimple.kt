package jy.cn.com.yframework.simple.dialog

import android.content.Context
import android.view.Gravity
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.dialog.BaseDialog
import jy.cn.com.ylibrary.util.YUnitUtil

/**

 * @Author Administrator
 * @Date 2019/9/28-12:02
 * @TODO
 */
class DialogSimple(context: Context, themeResId: Int) : BaseDialog(context, themeResId) {
    override fun getLayoutResId(): Int = R.layout.simple_dialog

    init {
        initCustomWidthAndHeightContentView(Gravity.CENTER, YUnitUtil.dp2px(context, 200f), YUnitUtil.dp2px(context, 200f))
    }
}