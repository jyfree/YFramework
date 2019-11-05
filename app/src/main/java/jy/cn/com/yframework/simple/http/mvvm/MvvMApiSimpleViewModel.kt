package jy.cn.com.yframework.simple.http.mvvm

import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jy.cn.com.ylibrary.base.mvvm.vm.BaseViewModel
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    YLogUtil.i("获取banner--成功", it)
                    loading.value = false
                    message.set(it.toString())
                }, {
                    loading.value = false
                    message.set("请求失败")
                })
        addDisposable(disposable)

    }
}