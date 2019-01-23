package com.whyble.farm.seed.view.friend.invite;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class InviteFriendPresenter implements InviteFriendIn.Presenter {

    InviteFriendIn.View view;

    InviteFriendModel model;

    public InviteFriendPresenter(InviteFriendIn.View view) {
        this.view = view;
        this.model = new InviteFriendModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getFriendInfo() {
        model.getFriendInfo(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setFriendInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
