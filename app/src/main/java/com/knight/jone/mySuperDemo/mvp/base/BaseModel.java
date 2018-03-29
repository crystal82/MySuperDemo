package com.knight.jone.mySuperDemo.mvp.base;

import com.knight.jone.mySuperDemo.mvp.http.Http;
import com.knight.jone.mySuperDemo.mvp.http.HttpService;
import com.knight.jone.mySuperDemo.mvp.mvp.IModel;

/**
 * Created by gaosheng on 2016/12/1.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
