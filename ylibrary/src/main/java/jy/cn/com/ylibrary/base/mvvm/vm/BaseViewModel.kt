package jy.cn.com.ylibrary.base.mvvm.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import jy.cn.com.ylibrary.base.contract.BaseContract

/**

 * @Author Administrator
 * @Date 2019/11/1-15:38
 * @TODO
 */
open class BaseViewModel<M : BaseContract.BaseModel> : ViewModel() {

    /**
     * 管理RxJava请求
     */
    private var compositeDisposable: CompositeDisposable? = null
    /**
     * 菊花
     */
    val loading = MutableLiveData<Boolean>()

    var mMode: M? = null

    fun attachMode(mode: M?) {
        mMode = mode
    }

    /**
     * 添加 rxJava 发出的请求
     */
    protected fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null || compositeDisposable?.isDisposed == true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
        compositeDisposable = null
    }
}