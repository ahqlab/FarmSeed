package com.whyble.farm.seed.view.board;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;

public class BoardPresenter implements BoardIn.Presenter {

    BoardIn.View view;

    BoardModel model;

    public BoardPresenter(BoardIn.View view) {
        this.view = view;
        this.model = new BoardModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getBoards() {
        model.getBoardContents(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setBoards(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
