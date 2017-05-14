package com.xingen.mvppractice.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class GsonUtils {
    /**
     * 解析Json成实体类
     * @param json
     * @param mClass
     * @param <T>
     * @return
     */
    public static <T> T paserJson(String json, Class<T> mClass) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, mClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 解析成List对象
     * @param json
     * @param List<T>
     * @param <T>
     * @return
     */
    public static <T> List<T> paserJson(String json,TypeToken<List<T>> typeToken){
        Gson gson = new Gson();
        List<T> list=null;
        try{
            list =  gson.fromJson(json,typeToken.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }
}
