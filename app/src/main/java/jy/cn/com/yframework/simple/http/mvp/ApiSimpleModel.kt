package jy.cn.com.yframework.simple.http.mvp

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import jy.cn.com.yframework.simple.http.api.ApiSimpleService
import jy.cn.com.yframework.simple.http.bean.BannerInfoListVo
import jy.cn.com.yframework.simple.http.bean.SendGiftVo
import jy.cn.com.yframework.simple.http.bean.base.HttpRequest
import jy.cn.com.ylibrary.base.model.BaseModel
import jy.cn.com.ylibrary.http.RxHelper
import jy.cn.com.ylibrary.http.RxObserver
import jy.cn.com.ylibrary.http.bean.HttpEntry
import jy.cn.com.ylibrary.http.bean.SingleBaseBean

/**

 * @Author Administrator
 * @Date 2019/9/27-11:46
 * @TODO
 */
class ApiSimpleModel : BaseModel<ApiSimpleService>(ApiSimpleService::class.java), ApiSimpleContract.Model {

    override fun <E> sendGift(rxObserver: RxObserver<SingleBaseBean>, mView: LifecycleProvider<E>, sendGiftVo: SendGiftVo) {
        serviceManager.sendGift(HttpRequest.obtainHttpRequest(sendGiftVo))
                .compose(RxHelper.handleSingleResult())
                .bindToLifecycle(mView)
                .subscribe(rxObserver)
    }

    override fun <E> getBanner(rxObserver: RxObserver<BannerInfoListVo>, mView: LifecycleProvider<E>, showPlace: Int) {
        serviceManager.getBanner(HttpRequest.obtainHttpRequest(HttpEntry("showPlace", showPlace)))
                .compose(RxHelper.handleSingleResult())
                .bindToLifecycle(mView)
                .subscribe(rxObserver)
    }
}