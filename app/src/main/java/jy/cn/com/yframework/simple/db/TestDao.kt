package jy.cn.com.yframework.simple.db

import jy.cn.com.yframework.simple.db.bean.TestInfo


/**
 * @Author Administrator
 * @Date 2019/10/28-13:59
 * @TODO
 */
object TestDao {

    private var dbWrapper: DBWrapper<TestInfo>? = null

    init {
        dbWrapper = DBWrapper(TestInfo::class.java)
    }

    fun getInstance(): DBWrapper<TestInfo> {
        return dbWrapper!!
    }
}
