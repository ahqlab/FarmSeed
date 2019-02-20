package com.whyble.farm.seed.custom;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whyble.farm.seed.util.MathUtil;

import java.text.DecimalFormat;

public class binding {

    @BindingAdapter({"stringToMoneyType"})
    public static void loadImageOne(TextView textView, String textMoney) {
        int money = MathUtil.parseInt(textMoney);
        DecimalFormat dc = new DecimalFormat("###,###,###,###");
        String ch = dc.format(money);
        System.out.println("작업전 : " + money);
        System.out.println("작업후 : " + ch);
        textView.setText(ch);

    }
}
