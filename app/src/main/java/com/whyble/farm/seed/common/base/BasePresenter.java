package com.whyble.farm.seed.common.base;

import android.content.Context;
import android.util.Log;

import com.whyble.farm.seed.common.CommonModel;

public class BasePresenter implements BaseIn.Presenter {

    BaseIn.View view;

    BaseModel model;

    public BasePresenter(BaseIn.View view) {
        this.view = view;
        this.model = new BaseModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void order(String order) {
        Log.e("HJLEE", "order : " + order);
       /* model.orderPay(order, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.orderResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });*/
    }
}
