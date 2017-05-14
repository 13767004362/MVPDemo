package com.xingen.mvppractice.collectionmovie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.data.source.LoaderProvider;
import com.xingen.mvppractice.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class CollectionMoviePresenter implements CollectionMovieConstract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    private CollectionMovieConstract.View view;
    private LoaderManager loaderManager;
    private LoaderProvider loaderProvider;

    public CollectionMoviePresenter(CollectionMovieConstract.View view,
                                    LoaderManager loaderManager,LoaderProvider loaderProvider) {
        this.loaderManager = loaderManager;
        this.loaderProvider=loaderProvider;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
          initLoader();
    }
    private final  int LOADER_MOVIE_ID=1;
    private void initLoader() {
        loaderManager.initLoader(LOADER_MOVIE_ID,null,this);
    }

    @Override
    public void unbindView() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==LOADER_MOVIE_ID){
            return loaderProvider.createMovieLoader();
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
          switch (loader.getId()){
              case LOADER_MOVIE_ID:
                  if(cursor!=null&&cursor.moveToFirst()){
                      List<MovieData> list=new ArrayList<>();
                      do {
                          list.add(TransformUtils.transformMovieData(cursor));
                      }while (cursor.moveToNext());
                      this.view.loadCollectionMovie(list);
                  }else{
                      this.view.showToast("无收藏的电影");
                  }
                  break;
          }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
