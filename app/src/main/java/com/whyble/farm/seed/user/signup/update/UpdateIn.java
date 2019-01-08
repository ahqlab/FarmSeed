package com.whyble.farm.seed.user.signup.update;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public interface UpdateIn {

    interface View{

        void textSignupResult(String s);

        void findRecommendResult(String s);

        void setUserInfo(String s);
    }

    interface Presenter{

        void loadData(Context context);

        void textLogin(User user);

        void confirmRecommend(String recommend);

        void getUserInfo();

    }
}
