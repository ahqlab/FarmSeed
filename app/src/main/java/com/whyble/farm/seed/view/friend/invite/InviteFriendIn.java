package com.whyble.farm.seed.view.friend.invite;

import android.content.Context;

public class InviteFriendIn {

    interface View{

        void setFriendInfo(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getFriendInfo();
    }
}
