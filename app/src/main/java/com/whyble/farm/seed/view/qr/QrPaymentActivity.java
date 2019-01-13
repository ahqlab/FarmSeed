package com.whyble.farm.seed.view.qr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.whyble.farm.seed.MainActivity;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityQrPaymentBinding;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.user.signup.login.LoginActivity;
import com.whyble.farm.seed.util.ValidationUtil;
import com.whyble.farm.seed.view.seed.list.bonus.BonusSeedActivity;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;
import com.whyble.farm.seed.view.seed.list.my.MySeedActivity;
import com.whyble.farm.seed.view.seed.list.save.SaveSeedActivity;

public class QrPaymentActivity extends BaseActivity<QrPaymentActivity> implements QrPaymentIn.View ,  NavigationView.OnNavigationItemSelectedListener{

    ActivityQrPaymentBinding binding;

    QrPaymentIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_qr_payment);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_payment);
        binding.setActivity(QrPaymentActivity.this);
        presenter = new QrPaymentPresenter(QrPaymentActivity.this);
        presenter.loadData(QrPaymentActivity.this);
     /*   binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });*/

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
    protected BaseActivity<QrPaymentActivity> getActivityClass() {
        return QrPaymentActivity.this;
    }

    public void onClickBackBtn(View view){
        finish();
    }

    public void onClickQRCodeScan(View view) {
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivityClass(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            //Toast.makeText(getApplicationContext(), getString(R.string.required_permission_message), Toast.LENGTH_SHORT).show();
        } else {
            IntentIntegrator integrator = new IntentIntegrator(getActivityClass());
            integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setScanningRectangle(800, 800);
            integrator.setPrompt(getString(R.string.qr_message));
            integrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(getActivityClass(), R.string.cancelled, Toast.LENGTH_LONG).show();
            } else {
                //Log.e("HJLEE", ">>>>" + result.getContents());
                binding.qecodeId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            IntentIntegrator integrator = new IntentIntegrator(getActivityClass());
                            integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                            integrator.setScanningRectangle(800, 800);
                            integrator.setPrompt(getString(R.string.qr_message));
                            integrator.initiateScan();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.required_permission_message), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    public void onClickSendQRCode(View view) {
        if (ValidationUtil.isEmptyOfEditText(binding.qecodeId)) {
            super.showBasicOneBtnPopup(null, "주문번호를 입력하세요")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            presenter.order(binding.qecodeId.getText().toString());
        }
    }

    @Override
    public void sendResult(String s) {
        Gson gson = new Gson();
        ServerResponse response = gson.fromJson(s, ServerResponse.class);
        if (response.getResult().matches("3")) {
            super.showBasicOneBtnPopup(null, response.getMsg())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
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
