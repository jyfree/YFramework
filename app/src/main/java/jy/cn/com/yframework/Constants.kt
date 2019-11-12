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
        const val CODE_WX_LOGIN_AUTH_SUCCEED = 666//微信登录授权--成功
        const val CODE_WX_LOGIN_AUTH_CANCEL = 667//微信登录授权--取消
        const val CODE_WX_LOGIN_AUTH_FAIL = 668//微信登录授权--失败

        const val CODE_WX_SHARE_SUCCESS = 669//微信分享--成功
        const val CODE_WX_SHARE_CANCEL = 670//微信分享--取消
        const val CODE_WX_SHARE_FAIL = 671//微信分享--失败

        const val CODE_WX_PAY_SUCCESS = 672//微信支付--成功
        const val CODE_WX_PAY_CANCEL = 673//微信支付--取消
        const val CODE_WX_PAY_FAIL = 674//微信支付--失败

    }

    object URL {
        const val SHARE_IMAGE_URL = "http://img.wowolive99.com/120x120.png"//分享imageUrl
        const val SHARE_TARGET_URL = "http://www.wowolive99.com/activity/download_h5/index.html"//分享目标地址
    }
}