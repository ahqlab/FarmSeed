package com.whyble.farm.seed.view.sub.giftSeed;

import android.content.Context;

public class GifiSeedIn {

    interface View{

        void setMyTotalSeed(String s);

        void setSavePoint(String save_point);

        void setFarmPoint(String farm_point);

        void setCashPoint(String cash_point);

        void setSearchResult(String s);

        void setSendResult(String s);

        void setCurrentMySeed(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getMyTotlaSeeds();

        void getSearchUser(String s);

        void sendGiftSeed(String userId, String point);

        void currentMySeed();
    }
}
