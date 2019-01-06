package com.whyble.farm.seed.user.signup.login;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;
import com.whyble.farm.seed.domain.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void login(User user, final DomainCallBackListner<String> domainCallBackListner) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("valid_user", user.getId()));
        nameValuePairs.add(new BasicNameValuePair("passwd", user.getPasswd()));

        new AbstractOldAsyncTask("login_ok.php"){

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
