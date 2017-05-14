package com.xingen.mvppractice.movielist;

import android.util.Log;

import com.android.volley.VolleyError;
import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.data.entity.MovieList;
import com.xingen.mvppractice.data.source.local.LocalDataSource;
import com.xingen.mvppractice.data.source.remote.RemoteDataSource;
import com.xingen.mvppractice.data.source.remote.volley.StringBodyRequest;
import com.xingen.mvppractice.utils.GsonUtils;
import com.xingen.mvppractice.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieListPresenter implements MovieListConstract.Presenter, StringBodyRequest.RequestResultListener {
    private MovieListConstract.View view;
    private LocalDataSource<MovieData> localDataSource;
    private RemoteDataSource remoteDataSource;

    public MovieListPresenter(RemoteDataSource remoteDataSource, LocalDataSource<MovieData> localDataSource, MovieListConstract.View view) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void start() {
        loadRemoteTask();
    }

    /**
     *  豆瓣中电影的Api:
     */
    private final String URL = "https://api.douban.com/v2/movie/search?q=张艺谋";

    private final int REQUEST_MOVIELIST = 1;
    private final String TAG = MovieListPresenter.class.getSimpleName();

    /**
     * 开始加载远程的数据
     */
    private void loadRemoteTask() {
        remoteDataSource.excuteRequest(URL, REQUEST_MOVIELIST, TAG, this);
    }

    @Override
    public void collectionMovie(List<Movie> list) {
        List<MovieData> movieDataList = new ArrayList<>();
        for (Movie movie : list) {
            movieDataList.add(TransformUtils.transformMovies(movie));
        }
       int size= this.localDataSource.bulkInsert(movieDataList);
        if(size>0){
            if(isViewBind()){
                this.view.showToast("收藏成功，可在收藏页面查看");
            }
        }
    }

    @Override
    public void unbindView() {
        this.view = null;
    }
    @Override
    public void success(int requestId, String response) {
        Log.i(TAG," 响应的数据 "+response);
        switch (requestId) {
            case REQUEST_MOVIELIST:
                List<Movie> list = GsonUtils.paserJson(response, MovieList.class).getSubjects();
                this.view.loadMovieList(list);
                this.view.showToast("获取列表成功");
                break;
            default:
                break;
        }
    }

    @Override
    public void failure(int requestId, VolleyError error) {
        switch (requestId) {
            case REQUEST_MOVIELIST:
                if (isViewBind()) {
                    this.view.showToast("加载失败");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查View是否被绑定
     *
     * @return
     */
    private boolean isViewBind() {
        return this.view == null ? false : true;
    }
}
