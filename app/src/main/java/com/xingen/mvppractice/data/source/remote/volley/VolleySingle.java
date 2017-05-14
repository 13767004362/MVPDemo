package com.xingen.mvppractice.data.source.remote.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.xingen.mvppractice.ui.base.BaseApplication;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 * <p/>
 * Volley的工具类
 */
public class VolleySingle {
    private ImageLoader imageLoader;
    private static VolleySingle instance;
    private RequestQueue requestQueue;

    private VolleySingle() {
        Context context = BaseApplication.getAppContext();
        this.requestQueue = Volley.newRequestQueue(context);
        this.imageLoader = new ImageLoader(this.requestQueue, new VolleyBitmapCache(context, this.requestQueue));
    }
    public static VolleySingle getInstance() {
        if (instance == null) {
            synchronized (VolleySingle.class) {
                instance = new VolleySingle();
            }
        }
        return instance;
    }

    public void addRequest(Request<?> request) {
        this.requestQueue.add(request);
    }

    public void cancleRequest(String tag) {
        this.requestQueue.cancelAll(tag);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
