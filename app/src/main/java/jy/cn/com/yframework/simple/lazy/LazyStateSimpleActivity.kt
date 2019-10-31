package jy.cn.com.yframework.simple.lazy

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseFragmentActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import kotlinx.android.synthetic.main.simple_lazy_activity.*
import java.util.*


/**

 * @Author Administrator
 * @Date 2019/10/31-11:24
 * @TODO
 */
class LazyStateSimpleActivity : BaseFragmentActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, LazyStateSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_lazy_activity

    override fun initClassTag(): Any = LazyStateSimpleActivity::class.java.simpleName


    private val list = ArrayList<Fragment>()
    private val title = arrayOf("Java", "object-c", "swift", "kotlin")

    override fun initUI(savedInstanceState: Bundle?) {

        for (i in 0 until title.size) {
            list.add(LazyStateSimpleFragment.newInstance(title[i]))
            tab_Layout.addTab(tab_Layout.newTab().setText(title[i]))
        }
        val adapter = MyFragmentPagerAdapter()
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = list.size

        tab_Layout.setupWithViewPager(viewPager)
        tab_Layout.setTabsFromPagerAdapter(adapter)

    }

    private inner class MyFragmentPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment = list[position]

        override fun getCount(): Int = list.size

        override fun getPageTitle(position: Int): CharSequence? = title[position]
    }

}