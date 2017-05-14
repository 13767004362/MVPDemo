package com.xingen.mvppractice.data.source.remote;

import com.android.volley.Request;
import com.xingen.mvppractice.data.source.remote.volley.StringBodyRequest;
import com.xingen.mvppractice.data.source.remote.volley.VolleySingle;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class RemoteDataSourceImp implements RemoteDataSource {
    /**
     * 静态方式构建
     * @return
     */
    public static RemoteDataSource newInstance(){
        return  new RemoteDataSourceImp();
    }
    @Override
    public void excuteRequest(String url, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener) {
        excuteRequest(url,null,requestId,tag,resultListener);
    }

    @Override
    public void excuteRequest(String url, Map<String, String> headrMap, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener) {
       excuteRequest(Request.Method.GET,url,null,headrMap,requestId,tag,resultListener);
    }

    @Override
    public void excuteRequest(int method, String url, JSONObject jsonObject, Map<String, String> headrMap, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener) {
           excuteRequest(createRequest(method,url,jsonObject,headrMap,requestId,tag,resultListener));
    }

    @Override
    public void excuteRequest(int method, String url, JSONObject jsonObject, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener) {
        excuteRequest(Request.Method.GET,url,jsonObject,null,requestId,tag,resultListener);
    }

    /**
     *  创建不同body，Header的请求
     * @param method
     * @param url
     * @param jsonObject
     * @param headrMap
     * @param requestId
     * @param tag
     * @param resultListener
     * @return
     */
    private StringBodyRequest createRequest(int method, String url,JSONObject jsonObject, Map<String,String> headrMap, int requestId,String tag,StringBodyRequest.RequestResultListener resultListener){
        StringBodyRequest stringBodyRequest=new StringBodyRequest(method,url,jsonObject,requestId,resultListener);
        stringBodyRequest.setTag(tag);
        if(headrMap!=null){
            Set<Map.Entry<String,String>> headerSet = headrMap.entrySet();
            for (Map.Entry<String,String> entry:headerSet){
                stringBodyRequest.setHeader(entry.getKey(),entry.getValue());
            }
        }
        return  stringBodyRequest;
    }
    /**
     * 执行 Request
     * @param stringBodyRequest
     */
    private void excuteRequest(StringBodyRequest stringBodyRequest){
        VolleySingle.getInstance().addRequest(stringBodyRequest);
    }
}
