package jy.cn.com.ylibrary.util

/**
 * @Author jy
 * @Date 2019/8/8-16:51
 * @TODO 双击限制
 */
class DoubleUtils {

    private var lastClickTime: Long = 0
    private val TIME: Long = 800

    val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            if (time - lastClickTime < TIME) {
                return true
            }
            lastClickTime = time
            return false
        }
}
