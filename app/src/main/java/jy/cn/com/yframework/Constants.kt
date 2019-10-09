package jy.cn.com.yframework

/**

 * @Author Administrator
 * @Date 2019/9/27-15:39
 * @TODO 常量
 */
object Constants {

    //设计宽高
    const val SYSTEM_DESIGN_HEIGHT = 1334
    const val SYSTEM_DESIGN_WIDTH = 750

    /**
     * 私聊消息
     */
    object CmdType {

        const val HOME = 123
        const val ME = 456
    }

    object RxBus {
        const val CODE_REQUEST_WX_LOGIN = 666//请求微信登录
        const val CODE_WX_SHARE_SUCCESS = 667//微信分享成功
        const val CODE_WX_PAY_SUCCESS = 667//微信支付成功

    }

    object URL {
        const val SHARE_IMAGE_URL = "http://img.wowolive99.com/120x120.png"//分享imageUrl
        const val SHARE_TARGET_URL = "http://www.wowolive99.com/activity/download_h5/index.html"//分享目标地址
    }
}