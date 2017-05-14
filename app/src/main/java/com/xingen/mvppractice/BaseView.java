package com.xingen.mvppractice;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 *
 *
 */
public interface BaseView<T> {
    /**
     * 设置Presenter
     */
    void setPresenter(T t);
}
