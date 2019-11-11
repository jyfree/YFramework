package jy.cn.com.yframework.simple.mvvm

import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.yframework.databinding.SimpleMvvmNomodelActivityBinding
import jy.cn.com.ylibrary.base.mvvm.MvvMBaseNoViewModelActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.ToastUtil

/**

 * @Author Administrator
 * @Date 2019/11/1-11:26
 * @TODO mvvm示例
 */
class MvvMSimpleActivity : MvvMBaseNoViewModelActivity<SimpleMvvmNomodelActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, MvvMSimpleActivity::class.java)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        dataBinding.listener = this
    }

    override fun initLayoutID(): Int = R.layout.simple_mvvm_nomodel_activity

    fun onClickPosition(position: Int) {
        ToastUtil.showToast(this, position.toString())
    }
}