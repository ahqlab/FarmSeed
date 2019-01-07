package com.whyble.farm.seed.view.seed.list.save;

import android.content.Context;

public class SaveSeedIn {

    interface View{

        void getSeedResult(String s);

        void setSendReResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getSeeds();

        void sendRe(String s);
    }
}
