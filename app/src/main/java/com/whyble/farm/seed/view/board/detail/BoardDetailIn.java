package com.whyble.farm.seed.view.board.detail;

import android.content.Context;

public class BoardDetailIn {

    interface View{

        void setBoardResult(String s);
    }
    interface Presenter{

        void loadData(Context context);

        void getDetail(String no);
    }
}


