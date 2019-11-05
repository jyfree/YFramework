package jy.cn.com.yframework.simple.mvvm

import android.arch.lifecycle.Observer
import android.os.Bundle
import jy.cn.com.yframework.BR
import jy.cn.com.yframework.R
import jy.cn.com.yframework.databinding.SimpleMvvmApiFragmentBinding
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.base.mvvm.MvvMBaseLazyFragment

/**

 * @Author Administrator
 * @Date 2019/10/31-13:37
 * @TODO
 */
class MvvMLazySimpleFragmentOne : MvvMBaseLazyFragment<SharedViewModel, SimpleMvvmApiFragmentBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): MvvMLazySimpleFragmentOne {
            return MvvMLazySimpleFragmentOne()
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_mvvm_api_fragment

    override fun initClassTag(): Any = MvvMLazySimpleFragmentOne::class.java.simpleName


    override fun initView(savedInstanceState: Bundle?) {
        viewModel.loading.observe(this, Observer { show ->
            show?.let { showPopWindowLoading(it) }
        })
    }

    override fun initViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun initViewModelId(): Int = BR.sharedViewModel

    override fun initModel(): BaseContract.BaseModel = SharedModel()

    override fun isShareData(): Boolean = true


    override fun lazyLoad() {

    }

    override fun visibleToUser() {

    }

}