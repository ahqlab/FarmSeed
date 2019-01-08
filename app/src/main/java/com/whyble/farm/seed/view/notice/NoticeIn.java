package com.whyble.farm.seed.view.notice;

import android.content.Context;

public class NoticeIn {

    interface View{

        void writeNoticeResult(String result);
    }
    interface Presenter{

        void loadData(Context context);

        void writeNotice(String title, String email, String content);
    }
}
