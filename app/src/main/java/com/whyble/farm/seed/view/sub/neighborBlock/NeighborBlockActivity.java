package com.whyble.farm.seed.view.sub.neighborBlock;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.adapter.AbsractCommonAdapter;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityNeighborBlockBinding;
import com.whyble.farm.seed.databinding.AllFarmSeedListviewItemBinding;
import com.whyble.farm.seed.databinding.NeighborBlockListviewItemBinding;
import com.whyble.farm.seed.domain.neighborBlock.Block1;
import com.whyble.farm.seed.domain.neighborBlock.Block2;
import com.whyble.farm.seed.domain.neighborBlock.NeighborBlockList;
import com.whyble.farm.seed.domain.seeds.farm.all.Farm;
import com.whyble.farm.seed.domain.seeds.save.AllSaveList;
import com.whyble.farm.seed.view.seed.list.farm.FarmSeedActivity;

import java.util.List;


//NeighborBlock
public class NeighborBlockActivity extends BaseActivity<NeighborBlockActivity> implements NeighborBlockIn.View{

    ActivityNeighborBlockBinding binding;

    NeighborBlockIn.Presenter presenter;

    AbsractCommonAdapter<Block1> neighborBlockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_neighbor_block);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_neighbor_block);
        binding.setActivity(NeighborBlockActivity.this);
        presenter = new NeighborBlockPresenter(NeighborBlockActivity.this);
        presenter.loadData(NeighborBlockActivity.this);
        presenter.getSeeds();
        binding.toolbar.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    @Override
    protected BaseActivity<NeighborBlockActivity> getActivityClass() {
        return NeighborBlockActivity.this;
    }


    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        NeighborBlockList response = gson.fromJson(s, NeighborBlockList.class);
        setSaveSeedList(response.getBlock1(), response.getBlock2());
        setTotalSeed(response.getTotal());
    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체블록 : " + seed);
    }

    public void setSaveSeedList(List<Block1> list, List<Block2> list2) {
        neighborBlockAdapter = new AbsractCommonAdapter<Block1>(NeighborBlockActivity.this, list) {

            NeighborBlockListviewItemBinding adapterBinding;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = neighborBlockAdapter.inflater.inflate(R.layout.neighbor_block_listview_item, null);
                    adapterBinding = DataBindingUtil.bind(convertView);
                    adapterBinding.setDomain1(neighborBlockAdapter.data.get(position));
                    convertView.setTag(adapterBinding);
                } else {
                    adapterBinding = (NeighborBlockListviewItemBinding) convertView.getTag();
                    adapterBinding.setDomain1(neighborBlockAdapter.data.get(position));
                }
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                return adapterBinding.getRoot();
            }
        };
        binding.seedListview.setAdapter(neighborBlockAdapter);
    }

}
