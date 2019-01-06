package com.whyble.farm.seed.view.notice;

import android.content.Context;

public class NoticePresenter implements NoticeIn.Presenter {

    NoticeIn.View view;

    NoticeModel model;

    public NoticePresenter(NoticeIn.View view) {
        this.view = view;
        this.model = new NoticeModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }
}
