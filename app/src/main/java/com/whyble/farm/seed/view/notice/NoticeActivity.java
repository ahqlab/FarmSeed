package com.whyble.farm.seed.view.notice;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityNoticeBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.util.ValidationUtil;

public class NoticeActivity extends BaseActivity<NoticeActivity> implements NoticeIn.View{

    ActivityNoticeBinding binding;

    NoticeIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        binding.setActivity(NoticeActivity.this);
        presenter = new NoticePresenter(NoticeActivity.this);
        presenter.loadData(NoticeActivity.this);
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<NoticeActivity> getActivityClass() {
        return NoticeActivity.this;
    }


    public void onClickBackBtn(View view){
        finish();
    }

    public void onClickNoticeBtn(View view){
        if (ValidationUtil.isEmptyOfEditText(binding.title)) {
            super.showBasicOneBtnPopup(null, "제목을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.email)) {
            super.showBasicOneBtnPopup(null, "이메일를 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.content)) {
            super.showBasicOneBtnPopup(null, "내용 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else{
            Log.e("HJLEE", binding.title.getText().toString());
            presenter.writeNotice(binding.title.getText().toString(),binding.email.getText().toString(),binding.content.getText().toString());
        }
    }

    @Override
    public void writeNoticeResult(String result) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(result, ServerResponse.class);
        Log.e("HJLEE", response.toString());
        if(response.getResult().matches("0")){
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }else{
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }

    }
}
