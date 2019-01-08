package com.whyble.farm.seed.view.board.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityBoardDetailBinding;
import com.whyble.farm.seed.domain.notice.Notice;

public class BoardDetailActivity extends BaseActivity<BoardDetailActivity> implements BoardDetailIn.View{

    ActivityBoardDetailBinding binding;

    BoardDetailIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail);
        binding.setActivity(BoardDetailActivity.this);
        presenter = new BoardDetailPresenter(this);
        presenter.loadData(BoardDetailActivity.this);
        Intent intent = getIntent();
        String no = intent.getStringExtra("No");
        presenter.getDetail(no);

    }

    @Override
    protected BaseActivity<BoardDetailActivity> getActivityClass() {
        return BoardDetailActivity.this;
    }

    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void setBoardResult(String s) {
        Gson gson = new Gson();
        Notice notice = gson.fromJson(s, Notice.class);
        binding.setDomain(notice);
    }
}
