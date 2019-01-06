package com.whyble.farm.seed.util;

import android.widget.EditText;

public class ValidationUtil {

    public static boolean isEmptyOfEditText(EditText editText){
        if(editText.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
}
