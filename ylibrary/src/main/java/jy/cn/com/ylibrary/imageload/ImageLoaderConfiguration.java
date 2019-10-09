package jy.cn.com.ylibrary.imageload;

public class ImageLoaderConfiguration {


    private static ImageLoaderConfiguration mInstance;


    public static ImageLoaderConfiguration getInstance() {
        if (mInstance == null) {
            mInstance = new ImageLoaderConfiguration();
        }
        return mInstance;
    }

    public int loadImageResId;//加载中图片
    public int errorImageResId;//加载失败图片
    public int userIconImageResId;//默认头像图片

    public void initImageResId(int loadImageResId, int errorImageResId, int userIconImageResId) {
        this.loadImageResId = loadImageResId;
        this.errorImageResId = errorImageResId;
        this.userIconImageResId = userIconImageResId;
    }

}
