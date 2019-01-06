package com.whyble.farm.seed.view.seed.list.farm;

import android.content.Context;

public class FarmSeedIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getSeeds();
    }
}
