package jy.cn.com.yframework.simple.http.bean

import jy.cn.com.ylibrary.http.bean.SingleBaseBean

/**

 * @Author Administrator
 * @Date 2019/1/10-19:42
 * @TODO 微信支付
 */
data class WXPayVo(
        var payId: Long?,//支付订单号，可用于主动查询
        var appId: String?,//appId
        var partnerId: String?,//商户ID
        var prepayId: String?,//微信订单ID
        var packageValue: String?,//签名包
        var nonceStr: String?,//随机字符串
        var timeStamp: String?,//时间戳
        var sign: String?//	签名
) : SingleBaseBean()