package com.whyble.farm.seed.view.board;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityBoardBinding;
import com.whyble.farm.seed.databinding.BoardListviewItemBinding;
import com.whyble.farm.seed.domain.notice.Notice;
import com.whyble.farm.seed.domain.notice.NoticeList;
import com.whyble.farm.seed.domain.seeds.save.SaveList;
import com.whyble.farm.seed.view.board.detail.BoardDetailActivity;

import java.util.List;

public class BoardActivity extends BaseActivity<BoardActivity> implements BoardIn.View{

    ActivityBoardBinding binding;

    BoardIn.Presenter presenter;

    AbsractCommonAdapter<Notice> boardSeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board);
        binding.setActivity(BoardActivity.this);
        presenter = new BoardPresenter(BoardActivity.this);
        presenter.loadData(BoardActivity.this);
        presenter.getBoards();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBoards();
    }

    @Override
    protected BaseActivity<BoardActivity> getActivityClass() {
        return BoardActivity.this;
    }

    @Override
    public void setBoards(String s) {
        Gson gson = new Gson();
        NoticeList response = gson.fromJson(s, NoticeList.class);
        if(response.getNotice() != null){
            setNoticeList(response.getNotice());
        }
    }

    public void onClickBackBtn(View view){
        finish();
    }

    private void setNoticeList(List<Notice> list) {
        boardSeedAdapter = new AbsractCommonAdapter<Notice>(BoardActivity.this, list) {

            BoardListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = boardSeedAdapter.inflater.inflate(R.layout.board_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain(boardSeedAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (BoardListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain(boardSeedAdapter.data.get(position));
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardActivity.super.showToast(adapterBinding.getDomain().getNo());
                        Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                        intent.putExtra("No", adapterBinding.getDomain().getNo());
                        startActivity(intent);
                    }
                });
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                return adapterBinding.getRoot();
            }
        };
        binding.seedListview.setAdapter(boardSeedAdapter);
    }
}
