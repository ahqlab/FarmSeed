package com.whyble.farm.seed.view.sub.shipment;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class ShipmentPresenter implements ShipmentIn.Presenter {

    ShipmentIn.View view;

    ShipmentModel model;

    public ShipmentPresenter(ShipmentIn.View view) {
        this.view = view;
        this.model = new ShipmentModel();
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
