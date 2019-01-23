package com.whyble.farm.seed.view.sub.applyingForMerchant;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class ApplyingForMerchantPresenter implements ApplyingForMerchantIn.Presenter {

    ApplyingForMerchantIn.View view;

    ApplyingForMerchantModel model;

    public ApplyingForMerchantPresenter(ApplyingForMerchantIn.View view) {
        this.view = view;
        this.model = new ApplyingForMerchantModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void regist(ApplyingForMerchantActivity.ApplyingForMerchant domain) {
        model.regist(domain, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setRegistResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
