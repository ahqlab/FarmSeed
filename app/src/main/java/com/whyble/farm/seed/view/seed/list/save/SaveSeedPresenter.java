package com.whyble.farm.seed.view.seed.list.save;

import android.content.Context;

import com.whyble.farm.seed.MainModel;
import com.whyble.farm.seed.common.CommonModel;

public class SaveSeedPresenter implements SaveSeedIn.Presenter {

    SaveSeedIn.View view;

    SaveSeedModel model;

    public SaveSeedPresenter(SaveSeedIn.View view) {
        this.view = view;
        this.model = new SaveSeedModel();
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
    public void sendRe(String s) {
        model.sendRe(s, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setSendReResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
