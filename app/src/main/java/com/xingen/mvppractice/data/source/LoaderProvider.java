package com.xingen.mvppractice.data.source;

import android.content.Context;
import android.support.v4.content.CursorLoader;


import com.xingen.mvppractice.data.source.local.MovieConstract;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class LoaderProvider {
    private final Context context;
    public LoaderProvider(Context context){
        this.context=context;
    }

    public CursorLoader createMovieLoader(){
        return  new CursorLoader(this.context, MovieConstract.MOVIEDATA_URI,null,null,null,null);
    }
}
