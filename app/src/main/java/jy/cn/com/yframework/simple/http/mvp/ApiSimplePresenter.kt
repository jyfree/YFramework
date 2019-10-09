package jy.cn.com.yframework.simple.http.mvp

import jy.cn.com.yframework.simple.http.bean.SendGiftVo
import jy.cn.com.ylibrary.base.presenter.BasePresenter
import jy.cn.com.ylibrary.http.RxObserver

/**

 * @Author Administrator
 * @Date 2019/9/27-9:45
 * @TODO
 */
class ApiSimplePresenter : BasePresenter<ApiSimpleContract.View, ApiSimpleContract.Model>(), ApiSimpleContract.Presenter {

    override fun sendGift(sendGiftVo: SendGiftVo) {
        if (mView == null) return
        mView?.showPopWindowLoading(true)
        mMode.sendGift(RxObserver(doNext = { it ->
            mView?.showPopWindowLoading(false)
            mView?.updateInfo(it.msg)
        }, doError = { _, _ ->
            mView?.showPopWindowLoading(false)
        }), mView!!.lifecycleProvider, sendGiftVo)
    }

    override fun getBanner(showPlace: Int) {
        if (mView == null) return
        mView?.showPopWindowLoading(true)
        mMode.getBanner(RxObserver(doNext = { it ->
            mView?.showPopWindowLoading(false)
            mView?.updateInfo(it.data?.toString())
        }, doError = { _, _ ->
            mView?.showPopWindowLoading(false)
        }), mView!!.lifecycleProvider, showPlace)
    }


}