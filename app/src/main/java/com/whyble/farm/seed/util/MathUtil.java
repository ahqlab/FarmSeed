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

    public static int parseInt(String number){
        char[] numChar = number.toCharArray();
        int intValue = 0;
        int decimal = 1;
        for(int index = numChar.length ; index > 0 ; index --){
            if(index == 1 ){
                if(numChar[index - 1] == '-'){
                    return intValue * -1;
                } else if(numChar[index - 1] == '+'){
                    return intValue;
                }
            }
            intValue = intValue + (((int)numChar[index-1] - 48) * (decimal));
            System.out.println((int)numChar[index-1] - 48+ " " + (decimal));
            decimal = decimal * 10;
        }
        return intValue;
    }
}
