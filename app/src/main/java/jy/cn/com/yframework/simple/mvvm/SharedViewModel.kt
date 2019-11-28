package jy.cn.com.yframework.simple.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import jy.cn.com.ylibrary.base.mvvm.vm.BaseViewModel
import jy.cn.com.ylibrary.http.RxDoError
import jy.cn.com.ylibrary.util.YLogUtil


/**

 * @Author Administrator
 * @Date 2019/11/4-15:41
 * @TODO
 */
class SharedViewModel : BaseViewModel<SharedModel>() {

    private val message = MutableLiveData<String>()

    fun setMessage(msg: String) {
        message.value = msg
    }

    fun getMessage(): LiveData<String> {
        return message
    }

    fun getBanner(showPlace: Int) {
        YLogUtil.i("获取banner--showPlace", showPlace, mMode)
        loading.value = true
        val disposable = mMode.getBanner(1)
                .subscribe({ it ->
                    YLogUtil.i("获取banner--成功", it)
                    loading.value = false
                    setMessage(it.toString())
                }, {
                    RxDoError.onError(it)
                    loading.value = false
                    setMessage("请求失败")
                })
        addDisposable(disposable)

    }
}