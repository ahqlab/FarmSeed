package com.whyble.farm.seed.util;

public class MathUtil {

    public static String stringToMoneyType(String str){
        return String.format("%,d", Integer.parseInt(str));
    }
}
