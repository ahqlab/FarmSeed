package com.whyble.farm.seed.view.sub.shipment;

import android.content.Context;

public class ShipmentIn {

    interface View{

        void getSeedResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getSeeds();
    }
}
