package com.xingen.mvppractice.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.utils.TransformUtils;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieLocalSource implements LocalDataSource<MovieData> {
    private ContentResolver contentResolver;
    public MovieLocalSource(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }
    @Override
    public List<MovieData> queryAll() {
        //查询工作，由CursorLoader已经完成
        return null;
    }

    @Override
    public List<MovieData> queryAction(String select, String[] selectArg) {
        //查询工作，由CursorLoader已经完成
        return null;
    }

    @Override
    public long insert(MovieData movieData) {
       ContentValues contentValues= TransformUtils.transformMovieData(movieData);
         Uri uri=this.contentResolver.insert(MovieConstract.MOVIEDATA_URI,contentValues);
         if(uri!=null){
             String s=   uri.toString();
             long rowId=Long.valueOf(s.substring(s.lastIndexOf("/",s.length())));
             return rowId;
         }
        return -1;
    }

    @Override
    public int bulkInsert(List<MovieData> list) {
        ContentValues[] contentValuesArray=new ContentValues[list.size()];
        for (int i=0;i<list.size();++i){
           contentValuesArray[i]=  TransformUtils.transformMovieData(list.get(i));
        }
      return   this.contentResolver.bulkInsert(MovieConstract.MOVIEDATA_URI,contentValuesArray);
    }

    @Override
    public int update(MovieData movieData, String select, String[] selectArg) {
        return 0;
    }

    @Override
    public int delite(MovieData movieData, String select, String[] selectArg) {
        return 0;
    }

    @Override
    public void deliteAll() {

    }
}
