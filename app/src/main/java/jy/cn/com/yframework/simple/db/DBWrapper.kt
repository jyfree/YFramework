package jy.cn.com.yframework.simple.db

import jy.cn.com.ylibrary.db.BaseWrapper

/**

 * @Author Administrator
 * @Date 2019/10/29-9:47
 * @TODO 扩展db操作，可自定义增删改查
 */
class DBWrapper<T : Any> constructor(subClass: Class<T>) : BaseWrapper<T>(subClass) {

}