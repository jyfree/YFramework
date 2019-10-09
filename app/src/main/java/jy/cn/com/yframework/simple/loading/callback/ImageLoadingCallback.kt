package jy.cn.com.yframework.simple.loading.callback

import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.loadsir.callback.Callback

/**

 * @Author Administrator
 * @Date 2019/1/10-14:46
 * @TODO LoadSir 带默认图片加载页
 */
class ImageLoadingCallback : Callback() {
    override fun onCreateView(): Int = R.layout.loadsir_callback_image_loading
}