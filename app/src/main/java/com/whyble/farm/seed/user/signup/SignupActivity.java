package com.whyble.farm.seed.user.signup;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivitySignupBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.util.device.DeviceUtils;
import com.whyble.farm.seed.view.daum.DaumActivity;

import java.util.Calendar;

public class SignupActivity extends BaseActivity<SignupActivity> implements SignupIn.View {

    ActivitySignupBinding binding;

    SignupIn.Presenter presenter;

    private DatePickerDialog picker;

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
    protected void onStart() {
        super.onStart();
        setAutoPhoneNumber();
    }

    public void setAutoPhoneNumber() {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, TextManager.READ_PHONE_STATE_CODE);
            }
        } else {
            if (null != DeviceUtils.getPhoneNumber(SignupActivity.this)) {
                String userPhoneNumber = DeviceUtils.getPhoneNumber(SignupActivity.this);
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
                            if (null != DeviceUtils.getPhoneNumber(SignupActivity.this)) {
                                String userPhoneNumber = DeviceUtils.getPhoneNumber(SignupActivity.this);
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

    private void setPhoneumber(String userPhoneNumber) {
        binding.getDomain().setTel1(userPhoneNumber.substring(0, 3));
        binding.getDomain().setTel2(userPhoneNumber.substring(3, 7));
        binding.getDomain().setTel3(userPhoneNumber.substring(7, 11));
    }

    @Override
    protected BaseActivity<SignupActivity> getActivityClass() {
        return SignupActivity.this;
    }

    public void textBtnClick(View view) {
        User user = new User("이형준", "llaallaall", "silver@gmail.com", "sd1213", "sd1213", "010", "9129", "9312",
                "12302", "대전광역시", "어디선가", "2020-12-12", "국민은행", "0123210", "masdj");
        Log.e("HJLEE", user.toString());
        presenter.textLogin(user);

    }

    @Override
    public void textSignupResult(String s) {
        Log.e("HJLEE", "s : " + s);
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        Log.e("HJLEE", "response : " + response.toString());
        if (response.getResult().matches("3")) {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        } else {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
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
        Log.e("HJLEE", s);
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

    public void onClickSignupBtnClick(View view) {
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
        } else if (ValidationUtil.isEmptyOfEditText(binding.passwd)) {
            super.showBasicOneBtnPopup(null, "새 비밀번호를 입력하세요.")
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
        } else if (ValidationUtil.isEmptyOfEditText(binding.address1)) {
            super.showBasicOneBtnPopup(null, "상세주소를 입력하세요")
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
            Log.e("HJLEE", binding.getDomain().toString());
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
        picker = new DatePickerDialog(SignupActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.getDomain().setBirthday(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        binding.birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //binding.birthday.editText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                        binding.getDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }
}
