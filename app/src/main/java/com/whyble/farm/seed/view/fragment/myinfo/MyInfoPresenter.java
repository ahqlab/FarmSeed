package com.whyble.farm.seed.view.fragment.myinfo;

import android.content.Context;

public class MyInfoPresenter implements MyInfoIn.Presenter {

    MyInfoIn.View view;

    MyInfoModel model;

    public MyInfoPresenter(MyInfoIn.View view) {
        this.view = view;
        this.model = new MyInfoModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }
}
