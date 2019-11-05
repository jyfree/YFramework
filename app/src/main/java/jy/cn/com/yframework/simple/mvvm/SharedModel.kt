package jy.cn.com.yframework.simple.mvvm

import io.reactivex.Observable
import jy.cn.com.yframework.simple.http.api.ApiSimpleService
import jy.cn.com.yframework.simple.http.bean.BannerInfoListVo
import jy.cn.com.yframework.simple.http.bean.base.HttpRequest
import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.base.model.BaseModel
import jy.cn.com.ylibrary.http.RxHelper
import jy.cn.com.ylibrary.http.bean.HttpEntry

/**

 * @Author Administrator
 * @Date 2019/11/4-15:42
 * @TODO
 */
class SharedModel : BaseModel<ApiSimpleService>(ApiSimpleService::class.java), BaseContract.BaseModel {


    fun getBanner(showPlace: Int): Observable<BannerInfoListVo> {
        return serviceManager.getBanner(HttpRequest.obtainHttpRequest(HttpEntry("showPlace", showPlace)))
                .compose(RxHelper.handleSingleResult())
    }
}