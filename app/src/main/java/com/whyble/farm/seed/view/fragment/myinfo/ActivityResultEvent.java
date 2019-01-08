package com.whyble.farm.seed.view.fragment.myinfo;

import android.content.Intent;

import lombok.Data;

@Data
public class ActivityResultEvent {

    private int requestCode;

    private int resultCode;

    private Intent data;

    public ActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }



}
