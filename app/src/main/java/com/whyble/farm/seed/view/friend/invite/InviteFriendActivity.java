package com.whyble.farm.seed.view.friend.invite;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityInviteFriendBinding;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.util.device.DeviceUtils;

import java.io.Serializable;

import lombok.Data;

public class InviteFriendActivity extends BaseActivity<InviteFriendActivity> implements InviteFriendIn.View{

    ActivityInviteFriendBinding binding;

    InviteFriendIn.Presenter presenter;

    SharedPrefManager sharedPrefManager;

    FriendResultResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_friend);
        binding.setActivity(InviteFriendActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(InviteFriendActivity.this);
        presenter = new InviteFriendPresenter(InviteFriendActivity.this);
        presenter.loadData(InviteFriendActivity.this);
        presenter.getFriendInfo();
        binding.blockId.setText("개인 블럭 ID : " + sharedPrefManager.getStringExtra(TextManager.VALID_USER));
    }

    @Override
    protected BaseActivity<InviteFriendActivity> getActivityClass() {
        return InviteFriendActivity.this;
    }


    public void onClickCopyAddress(View view){
        if(result != null){
            DeviceUtils.setClipBoardLink(InviteFriendActivity.this, result.getCode());
        }
    }

    public void onClickQRDownload(View view){

    }

    @Override
    public void setFriendInfo(String s) {
        if(s != null){
            Gson gson = new Gson();
            result = gson.fromJson(s, FriendResultResult.class);
            Picasso.with(InviteFriendActivity.this)
                    .load(result.getImg())
                    .into(binding.qecode);

        }
    }

    @Data
    public class FriendResultResult implements Serializable {

        private String code;

        private String img;
    }
}
