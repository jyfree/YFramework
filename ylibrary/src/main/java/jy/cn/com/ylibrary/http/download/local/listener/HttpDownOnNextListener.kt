package jy.cn.com.ylibrary.http.download.local.listener

abstract class HttpDownOnNextListener<T>{
    /**
     * 成功后回调方法
     * @param t
     */
    abstract fun onNext(t: T)

    /**
     * 开始下载
     */
    abstract fun onStart()

    /**
     * 完成下载
     */
    abstract fun onComplete()

    /**
     * 下载进度
     * @param readLength
     * @param countLength
     */
    abstract fun updateProgress(readLength: Long, countLength: Long)

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
    fun onError(e: Throwable) {

    }

    /**
     * 暂停下载
     */
    fun onPuase() {

    }

    /**
     * 停止下载销毁
     */
    fun onStop() {

    }
}