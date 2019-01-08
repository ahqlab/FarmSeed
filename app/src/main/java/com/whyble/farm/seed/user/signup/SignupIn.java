package com.whyble.farm.seed.user.signup;

import android.content.Context;

import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;

public interface SignupIn {

    interface View{

        void textSignupResult(String s);

        void findRecommendResult(String s);
    }

    interface Presenter{

        void loadData(Context context);

        void textLogin(User user);

        void confirmRecommend(String recommend);
    }
}
