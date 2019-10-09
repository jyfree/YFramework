package jy.cn.com.ylibrary.interceptor

import android.view.MotionEvent
import android.view.View
import jy.cn.com.ylibrary.util.ViewUtils

/**

 * @Author Administrator
 * @Date 2019/9/25-22:20
 * @TODO
 */
class PopupWindowTouchInterceptor : View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> v.performClick()
        }
        return !ViewUtils.isTouchInsideView(event, v)
    }
}