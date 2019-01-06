package com.whyble.farm.seed.view.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;

public class TestActivity extends BaseActivity<TestActivity>  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    protected BaseActivity<TestActivity> getActivityClass() {
        return null;
    }
}
