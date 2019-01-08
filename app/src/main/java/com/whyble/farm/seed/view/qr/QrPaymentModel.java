package com.whyble.farm.seed.view.qr;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;

public class QrPaymentModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

}
