package com.whyble.farm.seed.view.sub.applyingForMerchant;

import android.content.Context;

import com.whyble.farm.seed.common.CommonModel;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.common.Task.AbstractOldAsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ApplyingForMerchantModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void regist(ApplyingForMerchantActivity.ApplyingForMerchant domain, final DomainCallBackListner<String> domainCallBackListner) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("valid_user", domain.getValid_user()));
        nameValuePairs.add(new BasicNameValuePair("m_comname", domain.getM_comname()));
        nameValuePairs.add(new BasicNameValuePair("m_category", domain.getM_category()));
        nameValuePairs.add(new BasicNameValuePair("m_name", domain.getM_name()));
        nameValuePairs.add(new BasicNameValuePair("m_number", domain.getM_number()));
        nameValuePairs.add(new BasicNameValuePair("m_addr1", domain.getM_addr1()));
        nameValuePairs.add(new BasicNameValuePair("m_addr2", domain.getM_addr2()));
        nameValuePairs.add(new BasicNameValuePair("m_addr3", domain.getM_addr3()));
        nameValuePairs.add(new BasicNameValuePair("m_tel", domain.getM_tel()));
        nameValuePairs.add(new BasicNameValuePair("m_email", domain.getM_email()));
        nameValuePairs.add(new BasicNameValuePair("m_bankname", domain.getM_bankname()));
        nameValuePairs.add(new BasicNameValuePair("m_banknum", domain.getM_banknum()));
        nameValuePairs.add(new BasicNameValuePair("m_content", domain.getM_content()));

        new AbstractOldAsyncTask("membership_go.php") {

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
