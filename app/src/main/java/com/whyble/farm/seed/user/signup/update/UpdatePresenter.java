package com.whyble.farm.seed.user.signup.update;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.user.signup.SignupModel;
import com.whyble.farm.seed.view.fragment.myinfo.MyInfoModel;

public class UpdatePresenter implements UpdateIn.Presenter{

    UpdateIn.View view;

    UpdateModel model;

    SignupModel signupModel;

    MyInfoModel myInfoModel;

    public UpdatePresenter(UpdateIn.View view) {
        this.view = view;
        this.model = new UpdateModel();
        this.signupModel = new SignupModel();
        this.myInfoModel = new MyInfoModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        signupModel.loadData(context);
        myInfoModel.loadData(context);
    }

    @Override
    public void textLogin(User user) {
        model.testLogin(user, new UpdateModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.textSignupResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void confirmRecommend(String recommend) {
        signupModel.findRecommend(recommend, new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.findRecommendResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getUserInfo() {
        myInfoModel.getUserInfo(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setUserInfo(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void memberSecession() {
        model.memberSecession(new CommonModel.DomainCallBackListner<String>() {
            @Override
            public void doPostExecute(String s) {
                view.setSecessionResult(s);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
