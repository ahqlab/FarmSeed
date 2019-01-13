package com.whyble.farm.seed.view.sub.neighborBlock;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class NeighborBlockPresenter implements NeighborBlockIn.Presenter {

    NeighborBlockIn.View view;

    NeighborBlockModel model;

    public NeighborBlockPresenter(NeighborBlockIn.View view) {
        this.view = view;
        this.model = new NeighborBlockModel();
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

    @Override
    public void getSeeds(String userId) {
        model.getSeeds(userId, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setDialogView(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
