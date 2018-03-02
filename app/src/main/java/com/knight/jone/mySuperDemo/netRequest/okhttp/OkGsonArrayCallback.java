package com.knight.jone.mySuperDemo.netRequest.okhttp;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类的用途 如果要将得到的json直接转化为集合 建议使用该类
 * 该类的onUi() onFailed()方法运行在主线程
 */
public abstract class OkGsonArrayCallback<T> implements Callback {
    private Handler handler = OkHttp3Utils.getHandler();


    /**
     * OkHttp请求成功主线程进行Ui更新
     *
     * @param list 将json数据转为集合
     */
    public abstract void onUi(List<T> list);

    /**
     * 请求失败主线程操作
     *
     * @param call
     * @param e
     */
    public abstract void onFailed(Call call, IOException e);

    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(call, e);
            }
        });
    }

    //请求json 并直接返回集合 主线程处理
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final List<T> mList = new ArrayList<T>();

        String    json  = response.body().string();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();

        Gson gson = new Gson();

        Class<T>          cls   = null;
        Class             clz   = this.getClass();
        ParameterizedType type  = (ParameterizedType) clz.getGenericSuperclass();
        Type[]            types = type.getActualTypeArguments();
        cls = (Class<T>) types[0];

        for(final JsonElement elem : array){
            //循环遍历把对象添加到集合
            mList.add((T) gson.fromJson(elem, cls));
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                onUi(mList);
            }
        });
    }
}