package com.xingen.mvppractice.data.source.local;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public interface  LocalDataSource<T> {
    /**
     * 获取全部
     * @return
     */
    List<T> queryAll();

    /**
     *  指定条件下的查询
     * @param select
     * @param selectArg
     * @return
     */
    List<T> queryAction(String select,String[] selectArg);

    /**
     * 新增
     * @param t
     * @return
     */
    long insert(T t);

    /**
     *  批量插入
     * @param list
     * @return
     */
    int bulkInsert( List<T> list);

    /**
     * 更新
     * @param t
     * @param select
     * @param selectArg
     * @return
     */
    int update(T t,String select,String[] selectArg);

    /**
     * 指定条件的删除
     * @param t
     * @param select
     * @param selectArg
     * @return
     */
    int delite(T t,String select,String[] selectArg);

    /**
     * 删除全部
     */
    void deliteAll();


}
