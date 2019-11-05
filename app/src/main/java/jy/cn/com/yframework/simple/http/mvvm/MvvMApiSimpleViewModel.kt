package jy.cn.com.yframework.simple.http.mvvm

import android.databinding.ObservableField
import jy.cn.com.ylibrary.base.mvvm.vm.BaseViewModel
import jy.cn.com.ylibrary.http.RxDoError
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/11/1-15:49
 * @TODO
 */
class MvvMApiSimpleViewModel : BaseViewModel<MvvMApiSimpleModel>() {

    val message = ObservableField<String>()

    fun getBanner(showPlace: Int) {
        YLogUtil.i("获取banner--showPlace", showPlace, mMode)
        loading.value = true
        val disposable = mMode?.getBanner(1)!!
                .subscribe({ it ->
                    YLogUtil.i("获取banner--成功", it)
                    loading.value = false
                    message.set(it.toString())
                }, {
                    RxDoError.onError(it)
                    loading.value = false
                    message.set("请求失败")
                })
        addDisposable(disposable)

    }
}