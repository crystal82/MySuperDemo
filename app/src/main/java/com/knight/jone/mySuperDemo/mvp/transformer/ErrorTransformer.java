package com.knight.jone.mySuperDemo.mvp.transformer;


import com.knight.jone.mySuperDemo.exception.ErrorType;
import com.knight.jone.mySuperDemo.exception.ExceptionEngine;
import com.knight.jone.mySuperDemo.exception.ServerException;
import com.knight.jone.mySuperDemo.mvp.base.BaseHttpResult;
import com.knight.jone.mySuperDemo.utils.Lg;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by gaosheng on 2016/11/6.
 * 23:00
 * com.example.gaosheng.myapplication.transformer
 */

public class ErrorTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T> {

    private static       ErrorTransformer errorTransformer = null;
    private static final String           TAG              = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> responseObservable) {

        return responseObservable.map(new Func1<BaseHttpResult<T>, T>() {
            @Override
            public T call(BaseHttpResult<T> httpResult) {

                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");

                Lg.e(TAG, httpResult.toString());

                if (httpResult.getStatus() != ErrorType.SUCCESS)
                    throw new ServerException(httpResult.getStatus(), httpResult.getMessage());
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器throwable
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformer<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
