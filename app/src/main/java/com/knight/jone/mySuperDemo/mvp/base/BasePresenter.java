package com.knight.jone.mySuperDemo.mvp.base;


import com.knight.jone.mySuperDemo.mvp.mvpInterface.IModel;
import com.knight.jone.mySuperDemo.mvp.mvpInterface.IPresenter;
import com.knight.jone.mySuperDemo.mvp.mvpInterface.IView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by GaoSheng on 2016/11/26.
 * 17:21
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.base
 */

public abstract class BasePresenter<V extends IView> implements IPresenter {
    private   WeakReference actReference;
    protected V             iView;

    public abstract HashMap<String, IModel> getiModelMap();

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    /**
     * @param models
     * @return
     * 添加多个model,如有需要
     */
    public abstract HashMap<String, IModel> loadModelMap(IModel... models);

}
