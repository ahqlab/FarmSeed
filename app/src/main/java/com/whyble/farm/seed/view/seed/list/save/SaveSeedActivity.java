package com.whyble.farm.seed.view.seed.list.save;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.databinding.ActivitySaveSeedBinding;

public class SaveSeedActivity extends AppCompatActivity {

    ActivitySaveSeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_save_seed);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_seed);
        binding.setActivity(this);
    }
}
