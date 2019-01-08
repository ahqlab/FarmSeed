package com.whyble.farm.seed.domain;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonSeedHistory implements Serializable {

    public CommonSeedHistory(String seed) {
        this.seed = seed;
    }

    private String seed;

    private String createDate;

}
