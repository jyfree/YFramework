package jy.cn.com.yframework.simple.lazy

import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseLazyFragment
import jy.cn.com.ylibrary.util.YLogUtil
import kotlinx.android.synthetic.main.simple_lazy_fragment.*

/**

 * @Author Administrator
 * @Date 2019/10/31-13:37
 * @TODO
 */
class LazySimpleFragment : BaseLazyFragment() {

    companion object {
        @JvmStatic
        fun newInstance(msg: String): LazySimpleFragment {
            val lazyFragment = LazySimpleFragment()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            lazyFragment.arguments = bundle
            return lazyFragment
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_lazy_fragment

    override fun initClassTag(): Any = LazySimpleFragment::class.java.simpleName

    private var msg: String? = null

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        msg = arguments?.getString("msg") ?: ""
        textView.text = msg
    }

    override fun lazyLoad() {
        YLogUtil.i("lazyLoad：$msg")
    }

    override fun visibleToUser() {
        YLogUtil.i("visibleToUser：$msg")
    }

}