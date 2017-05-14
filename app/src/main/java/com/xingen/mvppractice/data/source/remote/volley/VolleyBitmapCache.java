package com.xingen.mvppractice.data.source.remote.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 *
 *  LruCache缓存
 */
public class VolleyBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private static  final String TAG=VolleyBitmapCache.class.getSimpleName();
    private RequestQueue  requestQueue;
    private DiskBasedCache diskBasedCache;
    private VolleyBitmapCache(int maxSize) {
        super(maxSize);
    }

    public VolleyBitmapCache(Context context , RequestQueue  requestQueue) {
        this(getCacheSize(context));
        this.requestQueue=requestQueue;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String s) {
        Bitmap bitmap = null;
        bitmap = get(s);
      /*  if (bitmap == null) {
               String disKey=getDisKey(s);
            Log.i("testUrl",disKey);
             Cache.Entry  entry= requestQueue.getCache().get(disKey);
            if(entry!=null){
                byte[] b=entry.data;
                if(b!=null){
                    bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
                }
            }

        }*/
        return bitmap;
    }

    /**
     *LruCache中key=new StringBuilder(url.length() + 12).append("#W").append(maxWidth).append("#H").append(maxHeight).append(url).toString();
     .DisBaseCache中key=url
     * @param s
     * @return
     */
    public String getDisKey(String s){
     return   s.substring(s.indexOf("http"));
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        if (get(s) == null) {
            put(s, bitmap);
        }
        //网络请求时，已经做了磁盘缓存，这里不需再处理
    }

    // Returns a cache size equal to approximately three screens worth of images.
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 3;
    }
}
