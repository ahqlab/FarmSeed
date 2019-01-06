package com.whyble.farm.seed.view.seed.list.farm;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;
import com.whyble.farm.seed.util.TextManager.TextManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class FarmSeedModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }


    public void getSeeds(final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("valid_user", sharedPrefManager.getStringExtra(TextManager.VALID_USER)));

        new AbstractOldAsyncTask("page2.php"){

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
