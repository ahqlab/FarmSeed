package com.whyble.farm.seed.view.qr;

import android.content.Context;

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
}
