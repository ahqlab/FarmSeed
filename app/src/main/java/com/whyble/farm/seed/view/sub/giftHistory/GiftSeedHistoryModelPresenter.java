package com.whyble.farm.seed.view.sub.giftHistory;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class GiftSeedHistoryModelPresenter implements GiftSeedHistoryIn.Presenter {

    GiftSeedHistoryIn.View view;

    GiftSeedHistoryModel model;

    public GiftSeedHistoryModelPresenter(GiftSeedHistoryIn.View view) {
        this.view = view;
        this.model = new GiftSeedHistoryModel();
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
