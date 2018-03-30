package com.knight.jone.mySuperDemo.mvp;

import android.text.TextUtils;
import android.view.View;

import com.knight.jone.mySuperDemo.R;
import com.knight.jone.mySuperDemo.mvp.base.BaseActivity;
import com.knight.jone.mySuperDemo.mvp.contract.LoginContract;
import com.knight.jone.mySuperDemo.mvp.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements
        LoginContract.LoginView {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        //ButterKnife.inject(this);
    }

    @Override
    protected void otherViewClick(View view) {
        mPresenter.login(getUserName(), getPwd());
    }

    @Override
    public String getUserName() {
        return "UserName";
    }

    @Override
    public String getPwd() {
        return "password";
    }

    @Override
    public void loginSuccess(String str) {
        toast(str);
    }

    @Override
    public void loginFail(String failMsg) {
        toast(failMsg);
    }


    public boolean checkNull() {
        boolean isNull = false;
        if (TextUtils.isEmpty(getUserName())) {
            //inputEmail.setError("账号不能为空");
            isNull = true;
        } else if (TextUtils.isEmpty(getPwd())) {
            //inputPassword.setError("密码不能为空");
            isNull = true;
        }
        return isNull;
    }

}
