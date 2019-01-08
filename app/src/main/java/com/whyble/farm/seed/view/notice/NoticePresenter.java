package com.whyble.farm.seed.view.notice;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

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

    @Override
    public void writeNotice(String title, String email, String content) {
        model.sendNotice(title, email, content, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String result) {
                view.writeNoticeResult(result);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
