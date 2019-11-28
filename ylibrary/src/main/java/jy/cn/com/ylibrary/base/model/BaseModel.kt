package jy.cn.com.ylibrary.base.model

import jy.cn.com.ylibrary.base.contract.BaseContract
import jy.cn.com.ylibrary.http.ApiServiceManager

/**

 * @Author Administrator
 * @Date 2019/9/27-12:01
 * @TODO
 */
open class BaseModel<T>(service: Class<T>) : BaseContract.BaseModel {

    val serviceManager = ApiServiceManager.create(service)


}