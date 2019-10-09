package jy.cn.com.ylibrary.http

import jy.cn.com.ylibrary.util.ObjectUtils


object HttpRequestHelper {

    fun obtainHttpRequestFiled(body: MutableMap<String, Any>, vararg filed: Map.Entry<String, Any?>) {
        filed.forEach {
            if (it.value != null)
                body[it.key] = it.value!!
        }
    }

    fun obtainHttpRequestAny(body: MutableMap<String, Any>, any: Any) {
        any::class.java.declaredFields.forEach {
            val value = ObjectUtils.getValueByFieldName(it.name, any)
            if (value != null && "null" != value && "" != value)
                body[it.name] = value
        }
    }
}