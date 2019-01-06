package com.whyble.farm.seed.user.signup.login;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public class LoginPresenter implements LoginIn.Presenter{

    LoginIn.View view;

    LoginModel model;

    public LoginPresenter(LoginIn.View view) {
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void login(User user) {
        model.login(user, new LoginModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.loginResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
