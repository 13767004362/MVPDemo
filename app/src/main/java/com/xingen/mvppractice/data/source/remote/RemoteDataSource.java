package com.xingen.mvppractice.data.source.remote;

import com.xingen.mvppractice.data.source.remote.volley.StringBodyRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public interface RemoteDataSource {

    void excuteRequest(String url, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener);

    void excuteRequest(String url, Map<String, String> headrMap, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener);

    void excuteRequest(int method, String url, JSONObject jsonObject, Map<String, String> headrMap, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener);

    void excuteRequest(int method, String url, JSONObject jsonObject, int requestId, String tag, StringBodyRequest.RequestResultListener resultListener);
}
