package com.whyble.farm.seed.user.signup.update;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityUpdateBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.util.device.DeviceUtils;
import com.whyble.farm.seed.view.daum.DaumActivity;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

import java.util.Calendar;

public class UpdateActivity extends BaseActivity<UpdateActivity> implements UpdateIn.View, NavigationView.OnNavigationItemSelectedListener {

    ActivityUpdateBinding binding;

    UpdateIn.Presenter presenter;

    private DatePickerDialog picker;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update);
        binding.setActivity(this);
        presenter = new UpdatePresenter(UpdateActivity.this);
        presenter.loadData(UpdateActivity.this);
        presenter.getUserInfo();
        sharedPrefManager = SharedPrefManager.getInstance(UpdateActivity.this);
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivityClass(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected BaseActivity<UpdateActivity> getActivityClass() {
        return UpdateActivity.this;
    }

    public void onClickBackBtn(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void setAutoPhoneNumber() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateActivity.this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, TextManager.READ_PHONE_STATE_CODE);
            }
        } else {
            if (null != DeviceUtils.getPhoneNumber(UpdateActivity.this)) {
                String userPhoneNumber = DeviceUtils.getPhoneNumber(UpdateActivity.this);
                setPhoneumber(userPhoneNumber);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case TextManager.READ_PHONE_STATE_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            if (null != DeviceUtils.getPhoneNumber(UpdateActivity.this)) {
                                String userPhoneNumber = DeviceUtils.getPhoneNumber(UpdateActivity.this);
                                binding.tel1.setText(userPhoneNumber.substring(0, 3));
                                binding.tel2.setText(userPhoneNumber.substring(3, 7));
                                binding.tel3.setText(userPhoneNumber.substring(7, 11));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.required_permission_message), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void textSignupResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("6")) {
            finish();
        } else {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sharedPrefManager.removeAllPreferences();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        }
    }

    private void setPhoneumber(String userPhoneNumber) {
        if (userPhoneNumber != null) {
            binding.getDomain().setTel1(userPhoneNumber.substring(0, 3));
            binding.getDomain().setTel2(userPhoneNumber.substring(3, 7));
            binding.getDomain().setTel3(userPhoneNumber.substring(7, 11));
        }
    }

    public void onClickTel1(View view) {
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

    public void onClickBankname(View view) {
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

        if (ValidationUtil.isEmptyOfEditText(binding.recommend)) {
            super.showBasicOneBtnPopup(null, "추천인을 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            presenter.confirmRecommend(binding.getDomain().getRecommend());
        }
    }

    @Override
    public void findRecommendResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        super.showBasicOneBtnPopup(null, response.getMsg())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setUserInfo(String s) {
        Gson gson = new Gson();
        User user = gson.fromJson(s, User.class);
        binding.setDomain(user);
        setAutoPhoneNumber();
    }

    public void onClickSignupBtnClick(View view) {
        Log.e("HJLEE", binding.getDomain().toString());
        if (ValidationUtil.isEmptyOfEditText(binding.name)) {
            super.showBasicOneBtnPopup(null, "이름를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.id)) {
            super.showBasicOneBtnPopup(null, "아이디를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.oldPassword)) {
            super.showBasicOneBtnPopup(null, "이전 비밀번호를 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.passwd)) {
            super.showBasicOneBtnPopup(null, "비밀번호를 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.passwd2)) {
            super.showBasicOneBtnPopup(null, "확인 비밀번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.birthday)) {
            super.showBasicOneBtnPopup(null, "생년월일을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.tel1)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.tel2)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.tel3)) {
            super.showBasicOneBtnPopup(null, "전화번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.zipcorde)) {
            super.showBasicOneBtnPopup(null, "우편번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.address)) {
            super.showBasicOneBtnPopup(null, "주소를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.bankname)) {
            super.showBasicOneBtnPopup(null, "은행명을 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmptyOfEditText(binding.banknum)) {
            super.showBasicOneBtnPopup(null, "계좌번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            binding.getDomain().setOld_pass(binding.oldPassword.getText().toString());
            binding.getDomain().setPasswd(binding.passwd.getText().toString());
            binding.getDomain().setPasswd2(binding.passwd2.getText().toString());
            presenter.textLogin(binding.getDomain());
        }
    }


    public void onClickZipcodeBtnClick(View view) {
        Intent i = new Intent(getActivityClass(), DaumActivity.class);
        startActivityForResult(i, TextManager.ZIPCODE_REQUST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TextManager.ZIPCODE_REQUST_CODE) {
            if (resultCode == RESULT_OK) {
                String zipcorde = data.getStringExtra("zipcorde");
                String address = data.getStringExtra("address");
                binding.zipcorde.setText(zipcorde);
                binding.address.setText(address);
            }
        }
    }

    public void onClickBirthday(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(UpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.getDomain().setBirthday(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        binding.birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.draw_save_seed){
            Intent intent = new Intent(getApplicationContext(), SaveSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_farm_seed){
            Intent intent = new Intent(getApplicationContext(), FarmSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_harvest_history){
            Intent intent = new Intent(getApplicationContext(), MySeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.draw_bonus_seed){
            Intent intent = new Intent(getApplicationContext(), BonusSeedActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.logout){
            mSharedPrefManager.removeAllPreferences();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            ((MainActivity)MainActivity.mContext).finish();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
