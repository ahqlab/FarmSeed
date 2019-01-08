package com.whyble.farm.seed.view.qr;

import android.content.Context;

public class QrPaymentIn {

    interface View{

        void sendResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void order(String s);
    }
}
