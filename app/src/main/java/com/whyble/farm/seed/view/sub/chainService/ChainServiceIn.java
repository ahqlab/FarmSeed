package com.whyble.farm.seed.view.sub.chainService;

import android.content.Context;

public class ChainServiceIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getSeeds();
    }
}
