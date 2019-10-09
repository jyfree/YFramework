package jy.cn.com.yframework.simple.http.upload

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jy.cn.com.yframework.simple.http.api.ApiUploadSimpleService
import jy.cn.com.ylibrary.base.model.BaseModel
import jy.cn.com.ylibrary.http.MultipartBuilder
import jy.cn.com.ylibrary.http.RxFileUploadObserver
import jy.cn.com.ylibrary.http.RxHelper
import jy.cn.com.ylibrary.http.UploadFileRequestBody
import java.io.File

/**

 * @Author Administrator
 * @Date 2019/9/27-14:59
 * @TODO 上传文件
 */
class UploadModel : BaseModel<ApiUploadSimpleService>(ApiUploadSimpleService::class.java) {

    fun uploadHeadImage(file: File, fileUploadObserver: RxFileUploadObserver<String>) {
        val uploadFileRequestBody = UploadFileRequestBody(file, fileUploadObserver)
        serviceManager.uploadHeadImage(MultipartBuilder.fileToMultipartBody(file, uploadFileRequestBody))
                .subscribeOn(Schedulers.io())
                .compose(RxHelper.handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver)
    }
}