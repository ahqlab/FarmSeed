package com.whyble.farm.seed.view.seed.list.bonus;

import android.content.Context;

public class BonusIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getSeeds();
    }
}
