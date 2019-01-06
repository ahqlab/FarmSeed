package com.whyble.farm.seed.user.signup;

import android.content.Context;

import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;

public class SignupPresenter implements SignupIn.Presenter{

    SignupIn.View view;

    SignupModel model;

    public SignupPresenter(SignupIn.View view) {
        this.view = view;
        this.model = new SignupModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void textLogin(User user) {
        model.testLogin(user, new SignupModel.DomainCallBackListner<String>() {
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
