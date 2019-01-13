package com.whyble.farm.seed.user.signup.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.common.base.BaseNoQRActivity;
import com.whyble.farm.seed.databinding.ActivityLoginBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.user.signup.SignupActivity;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.ValidationUtil;

public class LoginActivity extends BaseActivity<LoginActivity> implements LoginIn.View{

    ActivityLoginBinding binding;

    LoginIn.Presenter presenter;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        binding.setDomain(new User());
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        presenter = new LoginPresenter(this);
        presenter.loadData(LoginActivity.this);

        String id = sharedPrefManager.getStringExtra(TextManager.VALID_USER);
        if(!id.matches("")){
            autoLogin();
        }
    }

    public void autoLogin(){
        String id = sharedPrefManager.getStringExtra(TextManager.VALID_USER);
        String passwd = sharedPrefManager.getStringExtra(TextManager.PASSWD);
        if(!id.matches("")){
            binding.getDomain().setId(id);
            binding.getDomain().setPasswd(passwd);
            presenter.login(binding.getDomain());
        }
    }

    @Override
    protected BaseActivity<LoginActivity> getActivityClass() {
        return LoginActivity.this;
    }


    public void loginBtnClick(View view){
       /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();*/
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
        }else{
            presenter.login(binding.getDomain());
        }
    }

    public void loginRegistClick(View view){
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if(response.getResult().matches("2")){
            //아이디 비밀번호 저장
            saveUserInfo();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
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

    private void saveUserInfo(){
        sharedPrefManager.putStringExtra(TextManager.VALID_USER,  binding.getDomain().getId());
        sharedPrefManager.putStringExtra(TextManager.PASSWD,  binding.getDomain().getPasswd());
    }


}
