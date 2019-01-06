package com.whyble.farm.seed.user.signup.login;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public interface LoginIn {

    interface View{

        void loginResult(String s);
    }

    interface Presenter{

        void loadData(Context context);

        void login(User user);
    }
}
