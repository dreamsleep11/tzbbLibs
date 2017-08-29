package com.dhcc.workbench.helper;

import com.dhcc.workbench.kernel.StackHelper;
import com.dhcc.workbench.kernel.SuperLog;
import com.dhcc.workbench.kernel.TPromise;
import com.dhcc.workbench.kernel.AttrGet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dreamsleep on 2017/8/17.
 */

public class TRequestPromise extends TPromise {
    private static String SERVER_URL = "http://localhost:8080";

    static {
        try {
            Class s = Class.forName(AttrGet.getPackageName() + ".BuildConfig");
            if (s.getField("SERVER_URL") != null) {
                SERVER_URL = String.valueOf(s.getField("SERVER_URL").get(null));
            }

        } catch (Exception e) {
            StackHelper.printStack(e);
        }
    }

    private String url;
    private Map<String, String> param;

    private TRequestPromise(String url) {
        this.url = SERVER_URL + url;
        SuperLog.e("--------原生网络请求开始---------");
        SuperLog.e("地址：" + this.url);
        param = new HashMap();
    }

    public static TRequestPromise get(String url) {
        return new TRequestPromise(url);
    }

    public TRequestPromise addParam(String key, String value) {
        param.put(key, value);
        return this;
    }

    public OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return client;
    }

    public Call makeCall(Request request) {
        return getClient().newCall(request);
    }

    @Override
    protected void execute() {
        try {
            SuperLog.e("参数："+param.toString());
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url);
//        requestBuilder.header(key,headers.get(key));
            FormBody.Builder params = new FormBody.Builder();
            for (String key : param.keySet()) {
                params.add(key, param.get(key));
            }
            requestBuilder.post(params.build());
            Request request = requestBuilder.build();
            Response response = makeCall(request).execute();
            if (response.code() == 200) {
                String result=response.body().string();
                SuperLog.e("返回值："+result);
                resolve(result);
                SuperLog.e("--------原生网络请求结束---------");
            } else {
                reject(new RuntimeException(response.code() + ":" + response.message()));
                SuperLog.e("--------原生网络请求结束---------");
            }
        } catch (IOException e) {
            e.printStackTrace();
            reject(e);
            SuperLog.e("--------原生网络请求结束---------");
        }
    }

    @Override
    protected void onEnd() {

    }
}
