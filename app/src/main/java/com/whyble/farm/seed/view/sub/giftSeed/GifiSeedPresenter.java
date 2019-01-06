package com.whyble.farm.seed.view.sub.giftSeed;

import android.content.Context;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainModel;
import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.domain.myFarm.TotalSeeds;

public class GifiSeedPresenter implements GifiSeedIn.Presenter {

    GifiSeedIn.View view;

    GifiSeedModel model;

    MainModel mainModel;

    public GifiSeedPresenter(GifiSeedIn.View view) {
        this.view = view;
        this.model = new GifiSeedModel();
        this.mainModel = new MainModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        mainModel.loadData(context);
    }

    @Override
    public void getMyTotlaSeeds() {
        mainModel.getMyTotalSeeds(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                Gson gson = new Gson();
                TotalSeeds totalSeeds = gson.fromJson(s, TotalSeeds.class);
                view.setSavePoint(totalSeeds.getSave_point());
                view.setFarmPoint(totalSeeds.getFarm_point());
                view.setCashPoint(totalSeeds.getCash_point());
                //view.setMyTotalSeed(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
