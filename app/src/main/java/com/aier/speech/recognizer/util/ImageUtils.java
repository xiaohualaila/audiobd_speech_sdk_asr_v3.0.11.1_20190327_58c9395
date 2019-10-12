package com.aier.speech.recognizer.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.aier.speech.recognizer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class ImageUtils {

    /**
     * 一般用法
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void image(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
//                .placeholder(R.drawable.perch)//设置占位图
//                .error(R.drawable.perch)//设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 一般用法
     *
     * @param context
     * @param fiel
     * @param imageView
     */
    public static void image(Context context, File fiel, ImageView imageView) {
        Glide.with(context)
                .load(fiel)
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 一般用法
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void imageCrossFade(Context context, int path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 圆形加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void imageCircle(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
//                .placeholder(R.drawable.iv_my_photo)//设置占位图
//                .error(R.drawable.iv_my_photo)//设置错误图片
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//关闭Glide的硬盘缓存机制
                .into(imageView);
    }


    public static void imageCorners(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)

                .bitmapTransform(new RoundedCornersTransformation(context,100,0))
//
//                .placeholder(R.drawable.perch)//设置占位图
//                .error(R.drawable.perch)//设置错误图片
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);
    }

    /**
     * 圆形加载
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void imageCircle(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(imageView);
    }

    /**
     * 圆角加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void imageRound(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context))
                .crossFade()
                .into(imageView);
    }

    /**
     * 圆形图片处理
     *
     * @author : Rain
     * @data : 2017/10/18 0018
     * @Description :
     */
    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 圆角图片处理
     *
     * @author : Rain
     * @data : 2017/10/18 0018
     * @Description :
     */
    public static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}
