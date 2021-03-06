package jy.cn.com.yframework.simple.http.bean.base

import jy.cn.com.ylibrary.http.HttpRequestHelper


class HttpRequest private constructor() {

    val header: HttpHeader = HttpHeader()
    val body: MutableMap<String, Any> = mutableMapOf()


    companion object {
        fun obtainHttpRequest(vararg filed: Map.Entry<String, Any?>): HttpRequest {
            val httpRequest = HttpRequest()
            val body = httpRequest.body
            HttpRequestHelper.obtainHttpRequestFiled(body, *filed)
            return httpRequest
        }

        fun obtainHttpRequest(any: Any): HttpRequest {
            val httpRequest = HttpRequest()
            val body = httpRequest.body
            HttpRequestHelper.obtainHttpRequestAny(body, any)
            return httpRequest
        }
    }
}