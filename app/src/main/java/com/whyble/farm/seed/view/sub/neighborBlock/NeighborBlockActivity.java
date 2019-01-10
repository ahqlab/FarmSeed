package com.whyble.farm.seed.view.sub.neighborBlock;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import com.whyble.farm.seed.view.sub.neighborBlock.adapter.BaseExpandableAdapter;

import java.util.ArrayList;
import java.util.List;


//NeighborBlock
public class NeighborBlockActivity extends BaseActivity<NeighborBlockActivity> implements NeighborBlockIn.View {

    ActivityNeighborBlockBinding binding;

    NeighborBlockIn.Presenter presenter;

    AbsractCommonAdapter<Block1> neighborBlockAdapter;

    private ArrayList<Block1> mGroupList = null;
    private ArrayList<ArrayList<Block2>> mChildList = null;
    private ArrayList<Block2> mChildListContent = null;


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

    public void onClickBackBtn(View view){
        finish();
    }

    @Override
    public void getSeedResult(String s) {
        Gson gson = new Gson();
        NeighborBlockList response = gson.fromJson(s, NeighborBlockList.class);
        if(response.getBlock1() != null){
            setSaveSeedList(response.getBlock1(), response.getBlock2());
            setTotalSeed(response.getTotal());
        }
    }

    private void setTotalSeed(String seed) {
        binding.totalSeed.setText("전체블록 : " + seed);
    }

    //abhiandroid

    public void setSaveSeedList(List<Block1> list, List<Block2> list2) {

        mGroupList = new ArrayList<Block1>();
        mChildList = new ArrayList<ArrayList<Block2>>();

        for (int i = 0; i < list.size(); i++) {
            mGroupList.add(list.get(i));
            mChildListContent = new ArrayList<Block2>();
            for (int j = 0; j < list2.size(); j++) {
                if(list.get(i).getUser_id().matches(list2.get(j).getUser_id())){
                    mChildListContent.add(list2.get(j));
                }
            }
            mChildList.add(mChildListContent);
        }

       /* mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();

        mGroupList.add("가위");
        mGroupList.add("바위");
        mGroupList.add("보");

        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");

        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);*/

        binding.seedListview.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

        // 그룹 클릭 했을 경우 이벤트
        binding.seedListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        binding.seedListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "c click = " + childPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        binding.seedListview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 그룹이 열릴 경우 이벤트
        binding.seedListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });



        // 그룹이 닫힐 경우 이벤트
        binding.seedListview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 그룹이 열릴 경우 이벤트
        binding.seedListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });


        /*neighborBlockAdapter = new AbsractCommonAdapter<Block1>(NeighborBlockActivity.this, list) {

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
        binding.seedListview.setAdapter(neighborBlockAdapter);*/
    }

}
