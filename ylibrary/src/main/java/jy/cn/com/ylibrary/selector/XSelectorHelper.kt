package jy.cn.com.ylibrary.selector

import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import jy.cn.com.ylibrary.util.YUnitUtil

object XSelectorHelper {
    fun getColorRes(@ColorRes colorRes: Int) = XSelector.getContext().resources.getColor(colorRes)

    fun getDrawableRes(@DrawableRes drawableRes: Int): Drawable = XSelector.getContext().resources.getDrawable(drawableRes)

    fun dip2px(dipValue: Float): Int = YUnitUtil.dp2px(XSelector.getContext(), dipValue)
}