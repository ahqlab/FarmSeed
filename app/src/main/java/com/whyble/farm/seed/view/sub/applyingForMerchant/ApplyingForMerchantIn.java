package com.whyble.farm.seed.view.sub.applyingForMerchant;

import android.content.Context;

public class ApplyingForMerchantIn {

    interface View{

        void setRegistResult(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void regist(ApplyingForMerchantActivity.ApplyingForMerchant domain);
    }
}
