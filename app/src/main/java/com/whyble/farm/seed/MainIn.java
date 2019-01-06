package com.whyble.farm.seed;

import android.content.Context;

import com.whyble.farm.seed.domain.User;

public interface MainIn {

    interface View{

        void getSeedResult(String s);
    }

    interface Presenter{

        void loadData(Context context);

        void getSeeds();

        void getMyTotlaSeeds();
    }
}
