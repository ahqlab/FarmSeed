package com.whyble.farm.seed;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.util.TextManager.TextManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainModel extends CommonModel {

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
        /*Call<ServerResponse> call = NetRetrofit.getInstance().getUserService().testLogin(user.getId(), user.getEmail(), user.getPasswd(),
                user.getPasswd2(), user.getTel1(), user.getTel2(), user.getTel3(), user.getZipcorde(),
                user.getAddress(), user.getAddress1(),user.getBirthday(), user.getBankname(), user.getBanknum(), user.getRecommend());*/

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

    public void getSeeds(final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("valid_user", sharedPrefManager.getStringExtra(TextManager.VALID_USER)));

        new AbstractOldAsyncTask("main.php"){

            @Override
            protected void doPostExecute(String d) {
                domainCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(nameValuePairs);
    }

    public void getMyTotalSeeds(final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("valid_user", sharedPrefManager.getStringExtra(TextManager.VALID_USER)));

        new AbstractOldAsyncTask("myfarm.php"){

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
