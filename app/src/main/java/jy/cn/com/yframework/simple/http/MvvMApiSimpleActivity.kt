package jy.cn.com.yframework.simple.http

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.yframework.databinding.SimpleMvvmApiActivityBinding
import jy.cn.com.yframework.simple.http.mvvm.MvvMApiSimpleModel
import jy.cn.com.yframework.simple.http.mvvm.MvvMApiSimpleViewModel
import jy.cn.com.ylibrary.BR
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.base.mvvm.MvvMBaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils

/**

 * @Author Administrator
 * @Date 2019/11/1-15:48
 * @TODO
 */
class MvvMApiSimpleActivity : MvvMBaseActivity<MvvMApiSimpleViewModel, SimpleMvvmApiActivityBinding>() {
    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, MvvMApiSimpleActivity::class.java)
        }
    }

    override fun initClassTag(): Any = MvvMApiSimpleActivity::class.java.simpleName

    override fun initLayoutID(): Int = R.layout.simple_mvvm_api_activity

    override fun initViewModelClass(): Class<MvvMApiSimpleViewModel> = MvvMApiSimpleViewModel::class.java

    override fun initViewModelId(): Int = BR.test

    override fun initModel(): BaseContract.BaseModel = MvvMApiSimpleModel()

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.loading.observe(this, Observer { show ->
            show?.let { showPopWindowLoading(it) }
        })

    }

}