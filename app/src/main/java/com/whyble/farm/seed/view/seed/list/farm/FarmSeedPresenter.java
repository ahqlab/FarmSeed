package com.whyble.farm.seed.view.seed.list.farm;

import android.content.Context;

import com.whyble.farm.seed.MainModel;
import com.whyble.farm.seed.common.CommonModel;

public class FarmSeedPresenter implements FarmSeedIn.Presenter {

    FarmSeedIn.View view;

    FarmSeedModel model;

    public FarmSeedPresenter(FarmSeedIn.View view) {
        this.view = view;
        this.model = new FarmSeedModel();
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
