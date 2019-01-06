package com.whyble.farm.seed.view.sub.giftSeed;

import android.content.Context;

public class GifiSeedIn {

    interface View{

        void setMyTotalSeed(String s);

        void setSavePoint(String save_point);

        void setFarmPoint(String farm_point);

        void setCashPoint(String cash_point);
    }
    interface Presenter{

        void loadData(Context context);

        void getMyTotlaSeeds();
    }
}