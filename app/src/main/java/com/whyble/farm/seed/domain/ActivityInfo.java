package com.whyble.farm.seed.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActivityInfo {

    public String barTitle;

    public ActivityInfo(String barTitle){
        this.barTitle = barTitle;
    }

    public String getBarTitle() {
        return barTitle;
    }

    public void setBarTitle(String barTitle) {
        this.barTitle = barTitle;
    }
}
