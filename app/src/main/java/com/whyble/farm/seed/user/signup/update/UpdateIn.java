package com.whyble.farm.seed.user.signup.update;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public interface UpdateIn {

    interface View{

        void textSignupResult(String s);
    }

    interface Presenter{

        void loadData(Context context);

        void textLogin(User user);
    }
}
