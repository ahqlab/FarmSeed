package com.whyble.farm.seed.user.signup.update;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;
import com.whyble.farm.seed.domain.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class UpdateModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void testLogin(User user, final DomainCallBackListner<String> domainCallBackListner) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", user.getId()));
        nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("old_pass", user.getOld_pass()));
        nameValuePairs.add(new BasicNameValuePair("passwd", user.getPasswd()));
        nameValuePairs.add(new BasicNameValuePair("passwd2", user.getPasswd2()));
        nameValuePairs.add(new BasicNameValuePair("tel1", user.getTel1()));
        nameValuePairs.add(new BasicNameValuePair("tel2", user.getTel2()));
        nameValuePairs.add(new BasicNameValuePair("tel3", user.getTel3()));
        nameValuePairs.add(new BasicNameValuePair("zipcorde", user.getZipcorde()));
        nameValuePairs.add(new BasicNameValuePair("address", user.getAddress()));
        nameValuePairs.add(new BasicNameValuePair("address1", user.getAddress1()));
        nameValuePairs.add(new BasicNameValuePair("birthday", user.getBirthday()));
        nameValuePairs.add(new BasicNameValuePair("bankname", user.getBirthday()));
        nameValuePairs.add(new BasicNameValuePair("banknum", user.getBankname()));
        nameValuePairs.add(new BasicNameValuePair("recommend", user.getRecommend()));

        new AbstractOldAsyncTask("modfiy_ok.php"){

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
