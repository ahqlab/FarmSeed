package com.whyble.farm.seed.user.signup.update;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public class UpdatePresenter implements UpdateIn.Presenter{

    UpdateIn.View view;

    UpdateModel model;

    public UpdatePresenter(UpdateIn.View view) {
        this.view = view;
        this.model = new UpdateModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void textLogin(User user) {
        model.testLogin(user, new UpdateModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.textSignupResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
