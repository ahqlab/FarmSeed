package com.whyble.farm.seed.view.test;

import android.content.Context;

import com.whyble.farm.seed.MainIn;
import com.whyble.farm.seed.MainModel;

public class TestPresenter implements TestIn.Presenter {

    TestIn.View view;

    TestModel model;

    public TestPresenter(TestIn.View view) {
        this.view = view;
        this.model = new TestModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }
}
