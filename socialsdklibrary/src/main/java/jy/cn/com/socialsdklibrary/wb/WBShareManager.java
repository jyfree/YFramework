package jy.cn.com.socialsdklibrary.wb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

import jy.cn.com.socialsdklibrary.constant.SDKShareType;
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkShareListener;
import jy.cn.com.socialsdklibrary.util.SDKLogUtil;

/**
 * Administrator
 * created at 2016/11/10 11:17
 * TODO:微博分享
 */
public class WBShareManager implements WbShareCallback {

    private Context mContext;

    /**
     * 微博分享的接口实例
     */
    private WbShareHandler wbShareHandler;

    private boolean hasText;
    private boolean hasImage;
    private boolean hasWebPage;

    private OnSocialSdkShareListener listener;

    public WBShareManager(Context context, OnSocialSdkShareListener listener) {
        this.mContext = context;
        this.listener = listener;
        initialize();

    }

    /**
     * 初始化微博接口实例 。
     */
    private void initialize() {
        // 创建微博 SDK 接口实例
        wbShareHandler = new WbShareHandler((Activity) mContext);
        // 注册到新浪微博
        wbShareHandler.registerApp();

        setShareAllType();

    }


    /**
     * Activity
     * onCreate处调用
     * onNewIntent处调用
     *
     * @param intent
     */
    public void doResultIntent(Intent intent) {
        wbShareHandler.doResultIntent(intent, this);
    }


    /**
     * 设置是否分享text文本
     *
     * @param hasText
     */
    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    /**
     * 设置是否分享图片
     *
     * @param hasImage
     */
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    /**
     * 设置是否分享网页
     *
     * @param hasWebPage
     */
    public void setHasWebpage(boolean hasWebPage) {
        this.hasWebPage = hasWebPage;
    }

    /**
     * 设置分享全部类型
     */
    public void setShareAllType() {
        hasText = true;
        hasImage = true;
        hasWebPage = true;
    }

    /**
     * 分享文本消息
     *
     * @param text 消息内容
     */
    public void shareTextMsg(String text) {
        hasText = true;
        hasImage = false;
        hasWebPage = false;
        shareMultiMsg(text, null, null, null, null, null, null);
    }

    /**
     * 分享图片消息
     *
     * @param bitmap 图片对象
     */
    public void shareImageMsg(Bitmap bitmap) {
        hasText = false;
        hasImage = true;
        hasWebPage = false;
        shareMultiMsg(null, bitmap, null, null, null, null, null);
    }

    /**
     * 分享网页消息
     *
     * @param title       标题
     * @param description 描述
     * @param defaultText 默认text
     * @param targetUrl   目标链接地址
     * @param thumbImage  缩略图
     */
    public void shareWebpageMsg(String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        hasText = false;
        hasImage = false;
        hasWebPage = true;
        shareMultiMsg(null, null, title, description, defaultText, targetUrl, thumbImage);
    }

    /**
     * 多种分享方式结合
     * 分享类型：SHARE_ALL_IN_ONE
     *
     * @param text        文本消息分享
     * @param bitmap      图片消息分享
     * @param title       标题（网页消息分享）
     * @param description 描述（网页消息分享）
     * @param defaultText 默认text（网页消息分享）
     * @param targetUrl   目标链接地址（网页消息分享）
     * @param thumbImage  缩略图（网页消息分享）
     */
    public void shareMultiMsg(String text, Bitmap bitmap, String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj(text);
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj(bitmap);
        }
        if (hasWebPage) {
            weiboMessage.mediaObject = getWebpageObj(title, description, defaultText, targetUrl, thumbImage);
        }
        wbShareHandler.shareMessage(weiboMessage, false);

    }

    /**
     * 创建文本消息对象。
     *
     * @param text 消息内容
     * @return 文本消息对象
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @param bitmap bitmap对象
     * @return 图片消息对象
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @param title       标题
     * @param description 描述
     * @param targetUrl   目标链接地址
     * @param thumbImage  缩略图  （注意：最终压缩过的缩略图大小不得超过 32kb)
     * @return 多媒体（网页）消息对象
     */
    private WebpageObject getWebpageObj(String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;
        mediaObject.setThumbImage(thumbImage);
        mediaObject.actionUrl = targetUrl;
        mediaObject.defaultText = defaultText;
        return mediaObject;
    }

    @Override
    public void onWbShareSuccess() {
        SDKLogUtil.i("微博分享授权--成功");
        listener.shareSuccess(SDKShareType.TYPE_WB);
    }

    @Override
    public void onWbShareCancel() {
        SDKLogUtil.i("微博分享授权--取消");
        listener.shareCancel(SDKShareType.TYPE_WB);
    }

    @Override
    public void onWbShareFail() {
        SDKLogUtil.e("微博分享授权--失败");
        listener.shareFail(SDKShareType.TYPE_WB, "分享失败");
    }
}
