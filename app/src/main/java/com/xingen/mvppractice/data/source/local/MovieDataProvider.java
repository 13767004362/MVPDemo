package com.xingen.mvppractice.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieDataProvider extends ContentProvider {
    private static final int TABLE_DIR=1;
    private static UriMatcher uriMatcher;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieConstract.AUTHORITY, MovieConstract.TABLE_NAME_MOVI,TABLE_DIR);
    }
    private MovieDataHelper dataHelper;
    @Override
    public boolean onCreate() {
        this.dataHelper=new MovieDataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                cursor = db.query(MovieConstract.TABLE_NAME_MOVI, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                break;
        }
        if(cursor!=null){
            //添加通知对象
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                return     "vnd.android.cursor.dir/vnd." + MovieConstract.AUTHORITY
                        + MovieConstract.TABLE_NAME_MOVI;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase=dataHelper.getWritableDatabase();
        Uri returnUri=null;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                long rowId= sqLiteDatabase.insert(MovieConstract.TABLE_NAME_MOVI,null,values);
                returnUri=Uri.parse("content://" + MovieConstract.AUTHORITY + "/"
                        + MovieConstract.TABLE_NAME_MOVI + "/" + rowId);
                break;
            default:
                break;
        }
        //通知，数据源发生改变
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=this.dataHelper.getWritableDatabase();
        int deliteRow=0;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                deliteRow=sqLiteDatabase.delete(MovieConstract.TABLE_NAME_MOVI,selection,selectionArgs);
                break;
            default:
                break;
        }
        //通知，数据源发生改变
        getContext().getContentResolver().notifyChange(uri,null);
        return deliteRow;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=dataHelper.getWritableDatabase();
        int updateRow=0;
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                updateRow=sqLiteDatabase.update(MovieConstract.TABLE_NAME_MOVI,values,selection,selectionArgs);
                break;
            default:
                break;
        }
        if(updateRow>0){     //通知，数据源发生改变
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return 0;
    }

    /**
     *  批量插入
     * @param uri
     * @param values
     * @return
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                SQLiteDatabase sqLiteDatabase=dataHelper.getWritableDatabase();
                sqLiteDatabase.beginTransaction();
                try {
                    for (int i=0;i<values.length;++i){
                        sqLiteDatabase.insert(MovieConstract.TABLE_NAME_MOVI,null,values[i]);
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    sqLiteDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return  values.length;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
