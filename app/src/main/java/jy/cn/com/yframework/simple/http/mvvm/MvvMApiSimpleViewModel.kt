package jy.cn.com.yframework.simple.http.mvvm

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jy.cn.com.ylibrary.base.viewmodel.BaseViewModel
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/11/1-15:49
 * @TODO
 */
class MvvMApiSimpleViewModel : BaseViewModel<MvvMApiSimpleModel>() {

    fun getBanner(showPlace: Int) {
        YLogUtil.i("获取banner--showPlace", showPlace,mMode)

        val disposable = mMode?.getBanner(1)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    YLogUtil.i("获取banner--成功", it)
                }, {

                })
        addDisposable(disposable)

    }
}