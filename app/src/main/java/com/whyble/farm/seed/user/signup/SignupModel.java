package com.whyble.farm.seed.user.signup;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractAsyncTask;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;
import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.network.retrofit.NetRetrofit;
import com.whyble.farm.seed.util.TextManager.TextManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SignupModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void testLogin(User user, final DomainCallBackListner<String> domainCallBackListner) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if(user.getName() != null){
            nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
        }
        if(user.getId() != null){
            nameValuePairs.add(new BasicNameValuePair("id", user.getId()));
        }
        if(user.getEmail() != null){
            nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
        }
        if(user.getPasswd() != null){
            nameValuePairs.add(new BasicNameValuePair("passwd", user.getPasswd()));
        }
        if(user.getPasswd2()!= null){
            nameValuePairs.add(new BasicNameValuePair("passwd2", user.getPasswd2()));
        }
        if(user.getTel1()!= null){
            nameValuePairs.add(new BasicNameValuePair("tel1", user.getTel1()));
        }
        if(user.getTel2() != null){
            nameValuePairs.add(new BasicNameValuePair("tel2", user.getTel2()));
        }
        if(user.getTel3() != null){
            nameValuePairs.add(new BasicNameValuePair("tel3", user.getTel3()));
        }
        if(user.getZipcorde() != null){
            nameValuePairs.add(new BasicNameValuePair("zipcorde", user.getZipcorde()));
        }
        if(user.getAddress() != null){
            nameValuePairs.add(new BasicNameValuePair("address", user.getAddress()));
        }
        if(user.getAddress1() != null){
            nameValuePairs.add(new BasicNameValuePair("address1", user.getAddress1()));
        }
        if(user.getBirthday() != null){
            nameValuePairs.add(new BasicNameValuePair("birthday", user.getBirthday()));
        }
        if(user.getBankname() != null){
            nameValuePairs.add(new BasicNameValuePair("bankname", user.getBankname()));
        }
        if(user.getBankname() != null){
            nameValuePairs.add(new BasicNameValuePair("banknum", user.getBankname()));
        }
        if(user.getRecommend() != null){
            nameValuePairs.add(new BasicNameValuePair("recommend", user.getRecommend()));
        }

        new AbstractOldAsyncTask("member_ok2.php"){

            @Override
            protected void doPostExecute(String d) {
                domainCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(nameValuePairs);
    }


    public void findRecommend(String recommend, final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("recommend", recommend));
        new AbstractOldAsyncTask("re_check.php"){

            @Override
            protected void doPostExecute(String d) {
                domainCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(nameValuePairs);
    }
}
