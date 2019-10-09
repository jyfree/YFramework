package jy.cn.com.ylibrary.http

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jy.cn.com.ylibrary.http.bean.BaseBean
import jy.cn.com.ylibrary.http.bean.SingleBaseBean
import jy.cn.com.ylibrary.util.YLogUtil

object RxHelper {


    private val gson = Gson()

    fun <T> handleResult(): ObservableTransformer<BaseBean<T>, T> =
            ObservableTransformer { upstream ->
                val flatMap = upstream.flatMap<T> { result ->
                    if (result.isSuccess) { // code: 200
                        if (result.data == null) Observable.error(ApiResultDataNullException()) else {
                            YLogUtil.i(gson.toJson(result))
                            createData(result.data)
                        }
                    } else {
                        Observable.error(ApiException(result.code, result.msg))
                    }
                }
                flatMap.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }

    private fun <T> createData(data: T): ObservableSource<out T>? {
        return Observable.create { e ->
            try {
                e.onNext(data)
                e.onComplete()
            } catch (ex: Exception) {
                e.onError(ex)
            }
        }
    }

    fun <T> handleSingleResult(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        val flatMap = upstream.flatMap<T> { result ->
            result as SingleBaseBean
            if (result.isSuccess) {
                createData(result)
            } else {
                Observable.error<T>(ApiException(result.code, result.msg))
            }
        }
        flatMap.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}