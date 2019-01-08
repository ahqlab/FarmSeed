package com.whyble.farm.seed.view.qr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityQrPaymentBinding;

public class QrPaymentActivity extends BaseActivity<QrPaymentActivity> implements QrPaymentIn.View{

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
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<QrPaymentActivity> getActivityClass() {
        return QrPaymentActivity.this;
    }
}
