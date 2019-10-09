package jy.cn.com.yframework.simple.http.api

import io.reactivex.Observable
import jy.cn.com.yframework.simple.http.bean.BannerInfoListVo
import jy.cn.com.yframework.simple.http.bean.base.HttpRequest
import jy.cn.com.ylibrary.http.bean.SingleBaseBean
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Administrator
 * created at 2018/12/11 15:08
 * description:主页接口
 */
interface ApiSimpleService {
    //获取banner
    @POST("call.do?action=home.banner")
    fun getBanner(@Body httpRequest: HttpRequest): Observable<BannerInfoListVo>

    //送礼
    @POST("call.do?action=roomBehavior.sendGift")
    fun sendGift(@Body httpRequest: HttpRequest): Observable<SingleBaseBean>
}