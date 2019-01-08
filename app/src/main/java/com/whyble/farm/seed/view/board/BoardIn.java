package com.whyble.farm.seed.view.board;

import android.content.Context;

public class BoardIn {

    interface View{

        void setBoards(String s);
    }
    interface Presenter{
        void loadData(Context context);

        void getBoards();
    }
}
