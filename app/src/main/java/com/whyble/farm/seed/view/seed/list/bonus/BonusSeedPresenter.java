package com.whyble.farm.seed.view.seed.list.bonus;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class BonusSeedPresenter implements BonusIn.Presenter {

    BonusIn.View view;

    BonusSeedModel model;

    public BonusSeedPresenter(BonusIn.View view) {
        this.view = view;
        this.model = new BonusSeedModel();
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
