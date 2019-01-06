package com.whyble.farm.seed.view.sub.neighborBlock;

import android.content.Context;

public class NeighborBlockIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getSeeds();
    }
}
