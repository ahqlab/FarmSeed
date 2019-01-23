package com.whyble.farm.seed.view.sub.findMerchants;

import android.content.Context;

public class FindMerchantsIn {

    interface View{

        void searchResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void searchMerchants(String str);
    }
}
