package com.whyble.farm.seed.view.sub.giftHistory;

import android.content.Context;

public class GiftSeedHistoryIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getSeeds();
    }
}
