package com.whyble.farm.seed.util;

public class MathUtil {

    public static String stringToMoneyType(String str){
        return String.format("%,d", Integer.parseInt(str));
    }

    public int harfUp(int x) {
        int y = x % 10000;
        x = (y >= 5000) ? x + (10000 - y) : x - y;
        return x;
    }
}
