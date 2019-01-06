package com.whyble.farm.seed;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.domain.User;

public class MainPresenter implements MainIn.Presenter{

    MainIn.View view;

    MainModel model;

    public MainPresenter(MainIn.View view) {
        this.view = view;
        this.model = new MainModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getSeeds() {
        model.getSeeds(new MainModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.getSeedResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getMyTotlaSeeds() {
        model.getMyTotalSeeds(new CommonModel.DomainCallBackListner<String>() {
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