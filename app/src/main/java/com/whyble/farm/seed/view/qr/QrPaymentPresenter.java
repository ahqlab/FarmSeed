package com.whyble.farm.seed.view.qr;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;


public class QrPaymentPresenter implements QrPaymentIn.Presenter {

    QrPaymentIn.View view;

    QrPaymentModel model;

    public QrPaymentPresenter(QrPaymentIn.View view) {
        this.view = view;
        this.model = new QrPaymentModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void order(String qrAdress) {
        model.sendPoint(qrAdress, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.sendResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
