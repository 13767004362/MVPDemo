package com.xingen.mvppractice.data.source.remote.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 *
 * 重写StringRequest
 *
 * 用途：
 *  1. 传输Json数据类型的Body
 *  2. 传输一些Header
 */

public class StringBodyRequest extends StringRequest  {
    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    /**
     * 自定义header:
     */
    private Map<String, String> headers;
    /**
     * post传递的参数
     */
    private final String mRequestBody;
    /**
     *  请求结果的监听器
     */
    private RequestResultListener resultListener;
    /**
     * 请求的id
     */
    private int requestId;

    public StringBodyRequest( String url, int requestId,StringBodyRequest.RequestResultListener resultListener) {
      this(Method.GET,url,null,requestId,resultListener);
    }
    public StringBodyRequest(int method, String url, JSONObject jsonObject,int requestId,StringBodyRequest.RequestResultListener resultListener) {
        super(method, url,null,null);
        this.headers = new HashMap<>();
        this.mRequestBody=(jsonObject==null?null:jsonObject.toString());
        this.resultListener=resultListener;
        this.requestId=requestId;
    }
    /**
     * 重写getHeaders(),添加自定义的header
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }
    /**
     * 设置请求的header
     * "Charset", "UTF-8"://编码格式：utf-8
     * "Cookie", coockie:////设置coockie
     * @param
     * @return
     */
    public Map<String, String> setHeader(String key, String content) {
        if(!TextUtils.isEmpty(key)&&!TextUtils.isEmpty(content)){
            headers.put(key, content);
        }
        return headers;
    }
    /**
     * 重写Content-Type:设置为json
     */
    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }
    /**
     * post参数类型
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }
    /**
     * post参数
     */
    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    /**
     * 将string编码成byte
     * @return
     * @throws AuthFailureError
     */
    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 重写传递异常的回调
     * @param error
     */
    @Override
    public void deliverError(VolleyError error) {
         this.resultListener.failure(requestId,error);
    }

    /**
     * 重写传递结果的回调
     * @param response
     */
    @Override
    protected void deliverResponse(String response) {
          this.resultListener.success(requestId,response);
    }

    /**
     * 自定义请求结果和异常的回调接口
     */
    public  interface  RequestResultListener{
          void success(int requestId,String response);
          void failure(int reqestId,VolleyError error);
    }
}
