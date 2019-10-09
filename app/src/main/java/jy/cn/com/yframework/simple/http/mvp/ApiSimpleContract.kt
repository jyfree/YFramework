package jy.cn.com.yframework.simple.http.mvp

import com.trello.rxlifecycle2.LifecycleProvider
import jy.cn.com.yframework.simple.http.bean.BannerInfoListVo
import jy.cn.com.yframework.simple.http.bean.SendGiftVo
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.http.RxObserver
import jy.cn.com.ylibrary.http.bean.SingleBaseBean

/**

 * @Author Administrator
 * @Date 2019/9/27-9:46
 * @TODO
 */
interface ApiSimpleContract {
    interface View : BaseContract.BaseView {
        fun updateInfo(msg: String?)
    }

    interface Presenter : BaseContract.BasePresenter<View, Model> {
        fun getBanner(showPlace: Int)
        fun sendGift(sendGiftVo: SendGiftVo)
    }

    interface Model : BaseContract.BaseModel {
        fun <E> getBanner(rxObserver: RxObserver<BannerInfoListVo>, mView: LifecycleProvider<E>, showPlace: Int)

        fun <E> sendGift(rxObserver: RxObserver<SingleBaseBean>, mView: LifecycleProvider<E>, sendGiftVo: SendGiftVo)
    }
}