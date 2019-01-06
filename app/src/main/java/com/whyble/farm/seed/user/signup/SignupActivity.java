package com.whyble.farm.seed.user.signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivitySignupBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.util.ValidationUtil;

public class SignupActivity extends BaseActivity<SignupActivity> implements SignupIn.View {

    ActivitySignupBinding binding;

    SignupIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.setActivity(this);
        binding.setDomain(new User());
        presenter = new SignupPresenter(this);
        presenter.loadData(SignupActivity.this);
    }

    @Override
    protected BaseActivity<SignupActivity> getActivityClass() {
        return SignupActivity.this;
    }

    public void textBtnClick(View view) {
        User user = new User("llaallaall", "silver@gmail.com", "sd1213", "sd1213", "010", "9129", "9312",
                "12302", "대전광역시", "어디선가", "2020-12-12", "국민은행", "0123210", "masdj");
        Log.e("HJLEE", user.toString());
        presenter.textLogin(user);

    }

    @Override
    public void textSignupResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        Log.e("HJLEE", "response : " + response.toString());
        if(response.getResult().matches("3")){
            finish();
            /*super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();*/
        }else{
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }


    }

    public void onClickTel1(View view){
        final String[] values = new String[]{
                "010",
                "011",
                "019",
                "018",
                "017",
                "016"
        };
        AlertDialog.Builder builder = super.showBasicOneBtnPopup(null, null);
        // add a radio button list
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.tel1.setText(values[which]);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onClickBankname(View view){
        final String[] values = new String[]{
                "농협",
                "하나은행",
                "카카오뱅크",
                "신한은행"
        };
        AlertDialog.Builder builder = super.showBasicOneBtnPopup("은행선택", null);
        // add a radio button list
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.bankname.setText(values[which]);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onClickConfirmRecommendBtnClick(View view) {

    }

    public void onClickSignupBtnClick(View view) {
        Log.e("HJLEE", binding.getDomain().toString());
        if(ValidationUtil.isEmptyOfEditText(binding.id)){
            super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.passwd)){
            super.showBasicOneBtnPopup(null, "비밀번호를 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.passwd2)){
            super.showBasicOneBtnPopup(null, "확인 비밀번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.birthday)) {
            super.showBasicOneBtnPopup(null, "생년월일을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.tel1)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.tel2)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.tel3)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.zipcorde)) {
            super.showBasicOneBtnPopup(null, "우편번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.address)) {
            super.showBasicOneBtnPopup(null, "주소를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.address1)) {
            super.showBasicOneBtnPopup(null, "상세주소를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.bankname)) {
            super.showBasicOneBtnPopup(null, "은행명을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(ValidationUtil.isEmptyOfEditText(binding.banknum)) {
            super.showBasicOneBtnPopup(null, "계좌번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            Log.e("HJLEE", binding.getDomain().toString());
            presenter.textLogin(binding.getDomain());
        }
    }


    public void onClickZipcodeBtnClick(View view) {
    }
}
