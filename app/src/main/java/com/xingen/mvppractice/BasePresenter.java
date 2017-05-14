package com.xingen.mvppractice;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public interface BasePresenter {
    /**
     * 开启任务
     */
    void start();

    /**
     * 解除对View的引用
     */
    void unbindView();
}
