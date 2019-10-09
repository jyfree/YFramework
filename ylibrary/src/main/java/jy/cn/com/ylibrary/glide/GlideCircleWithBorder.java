package jy.cn.com.ylibrary.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

public class GlideCircleWithBorder extends BitmapTransformation {
    private final String ID = getClass().getName();
    private Paint mBorderPaint;
    private float borderWidth;


    public GlideCircleWithBorder(float borderWidth, int borderColor) {
        this.borderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(borderWidth);
        mBorderPaint.setDither(true);

    }

    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight){
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool bitmapPool, Bitmap source) {

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap square = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = bitmapPool.get(size, size, Bitmap.Config.ARGB_8888);

        //画图
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        //设置 Shader
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float radius = size / 2f;
        //绘制一个圆
        canvas.drawCircle(radius, radius, radius, paint);


        //注意：避免出现描边被屏幕边缘裁掉
        float borderRadius = radius - (borderWidth / 2);
        //画边框
        canvas.drawCircle(radius, radius, borderRadius, mBorderPaint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NotNull MessageDigest messageDigest) {
        messageDigest.update(ID.getBytes(CHARSET));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideCircleWithBorder;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
