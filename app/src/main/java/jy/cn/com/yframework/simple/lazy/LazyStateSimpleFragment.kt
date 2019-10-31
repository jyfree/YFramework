package jy.cn.com.yframework.simple.lazy

import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseLazyStateFragment
import jy.cn.com.ylibrary.util.YLogUtil
import kotlinx.android.synthetic.main.simple_lazy_fragment.*

/**

 * @Author Administrator
 * @Date 2019/10/31-13:37
 * @TODO 懒加载--保存状态
 */
class LazyStateSimpleFragment : BaseLazyStateFragment() {

    companion object {
        @JvmStatic
        fun newInstance(msg: String): LazyStateSimpleFragment {
            val lazyFragment = LazyStateSimpleFragment()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            lazyFragment.arguments = bundle
            return lazyFragment
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_lazy_fragment

    override fun initClassTag(): Any = LazyStateSimpleFragment::class.java.simpleName

    private var msg: String? = null

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        msg = arguments?.getString("msg") ?: ""
        textView.text = msg
    }

    override fun onFirstTimeLaunched() {
        YLogUtil.i("onFirstTimeLaunched：$msg")
    }

    override fun onSaveState(outState: Bundle?) {
        outState?.putString("data", "保存状态$msg")
    }

    override fun onRestoreState(savedInstanceState: Bundle?) {
        val data = savedInstanceState?.getString("data")
        data?.let { textView.text = it }
    }

    override fun visibleToUser() {
        YLogUtil.i("visibleToUser：$msg")
    }

}