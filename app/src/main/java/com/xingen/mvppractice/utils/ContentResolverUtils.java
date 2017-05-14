package com.xingen.mvppractice.utils;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class ContentResolverUtils {
    public static ContentResolver createResolver(Context context){
        return context.getContentResolver();
    }
}
