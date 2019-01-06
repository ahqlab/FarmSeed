package com.whyble.farm.seed.view.seed.list.my;

import android.content.Context;

public class MySeedIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getSeeds();
    }
}
