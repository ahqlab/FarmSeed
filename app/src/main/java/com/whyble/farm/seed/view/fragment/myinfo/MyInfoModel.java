package com.whyble.farm.seed.view.fragment.myinfo;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;

public class MyInfoModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

}
