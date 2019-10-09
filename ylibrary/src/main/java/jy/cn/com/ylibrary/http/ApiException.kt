package jy.cn.com.ylibrary.http


class ApiException(code: Int, message: String) : Exception(message, Throwable(code.toString()))