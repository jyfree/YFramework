package jy.cn.com.yframework.simple.http.api

import io.reactivex.Observable
import jy.cn.com.ylibrary.http.bean.BaseBean
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.POST

/**

 * @Author Administrator
 * @Date 2019/9/27-14:57
 * @TODO 上传文件服务
 */
interface ApiUploadSimpleService {

    @POST("user/upload_headImg")
    fun uploadHeadImage(@Body body: MultipartBody): Observable<BaseBean<String>>
}