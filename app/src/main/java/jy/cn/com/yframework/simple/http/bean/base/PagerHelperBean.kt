package jy.cn.com.yframework.simple.http.bean.base

import jy.cn.com.ylibrary.http.bean.SingleBaseBean

data class PagerHelperBean<T>(
        var pageSize: Int,//页大小
        var pageNo: Int,//页码
        var pageCount: Int,//总页数
        var count: Int,//总条数
        var data: MutableList<T>?
) : SingleBaseBean()