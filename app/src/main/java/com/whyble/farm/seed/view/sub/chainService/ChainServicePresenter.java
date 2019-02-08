package com.whyble.farm.seed.view.sub.chainService;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class ChainServicePresenter implements ChainServiceIn.Presenter {

    ChainServiceIn.View view;

    ChainServiceModel model;

    public ChainServicePresenter(ChainServiceIn.View view) {
        this.view = view;
        this.model = new ChainServiceModel();
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
