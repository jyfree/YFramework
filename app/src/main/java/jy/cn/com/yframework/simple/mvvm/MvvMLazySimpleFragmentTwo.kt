package jy.cn.com.yframework.simple.mvvm

import android.os.Bundle
import jy.cn.com.yframework.BR
import jy.cn.com.yframework.R
import jy.cn.com.yframework.databinding.SimpleMvvmFragmentBinding
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.base.mvvm.MvvMBaseLazyFragment

/**

 * @Author Administrator
 * @Date 2019/11/5-9:51
 * @TODO
 */
class MvvMLazySimpleFragmentTwo : MvvMBaseLazyFragment<SharedViewModel, SimpleMvvmFragmentBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): MvvMLazySimpleFragmentTwo {
            return MvvMLazySimpleFragmentTwo()
        }
    }


    override fun initLayoutID(): Int = R.layout.simple_mvvm_fragment


    override fun initView(savedInstanceState: Bundle?) {
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