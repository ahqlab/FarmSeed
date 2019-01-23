package com.whyble.farm.seed.view.sub.findMerchants;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class FindMerchantsPresenter implements FindMerchantsIn.Presenter {

    FindMerchantsIn.View view;

    FindMerchantsModel model;

    public FindMerchantsPresenter(FindMerchantsIn.View view) {
        this.view = view;
        this.model = new FindMerchantsModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void searchMerchants(String str) {
        model.searchMerchants(str, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.searchResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getChainList() {
        model.getChainList(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setChainList(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
