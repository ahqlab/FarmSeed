package com.whyble.farm.seed.view.board.detail;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.view.board.BoardModel;

public class BoardDetailPresenter implements BoardDetailIn.Presenter {

    BoardDetailIn.View view;

    BoardModel model;

    public BoardDetailPresenter(BoardDetailIn.View view) {
        this.view = view;
        this.model = new BoardModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getDetail(String no) {
        model.getDetail(no, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setBoardResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
