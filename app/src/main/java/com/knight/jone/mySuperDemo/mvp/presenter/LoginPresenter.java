package com.knight.jone.mySuperDemo.mvp.presenter;

import com.knight.jone.mySuperDemo.mvp.LoginActivity;
import com.knight.jone.mySuperDemo.mvp.base.BasePresenter;
import com.knight.jone.mySuperDemo.mvp.contract.LoginContract;
import com.knight.jone.mySuperDemo.mvp.model.LoginModel;
import com.knight.jone.mySuperDemo.mvp.mvp.IModel;
import com.knight.jone.mySuperDemo.utils.Lg;

import java.util.HashMap;

/**
 * Created by GaoSheng on 2016/11/26.
 * 20:51
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.presenter
 */

public class LoginPresenter extends BasePresenter<LoginActivity> implements
        LoginContract.LoginPresenter {

    @Override
    public void login(String name, String pwd) {
        if (!getIView().checkNull()) {
            ((LoginModel) getiModelMap().get("login")).login(name, pwd, new LoginModel
                    .InfoHint() {
                @Override
                public void successInfo(String str) {
                    getIView().loginSuccess(str);  //成功
                }

                @Override
                public void failInfo(String str) {
                    Lg.e("LoginPresenter.failInfo", str);

                    getIView().loginFail(str);  //失败
                }
            });
        }
    }


    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }
}
