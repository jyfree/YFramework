package jy.cn.com.ylibrary.http.bean

class HttpListEntry(override val key: String, override val value: List<String>) : Map.Entry<String, List<String>>