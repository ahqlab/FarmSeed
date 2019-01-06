package com.whyble.farm.seed.view.seed.list.my;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class MySeedPresenter implements MySeedIn.Presenter {

    MySeedIn.View view;

    MySeedModel model;

    public MySeedPresenter(MySeedIn.View view) {
        this.view = view;
        this.model = new MySeedModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getSeeds() {
        model.getSeeds(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.getSeedResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
