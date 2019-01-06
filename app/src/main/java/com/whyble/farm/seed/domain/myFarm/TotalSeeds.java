package com.whyble.farm.seed.domain.myFarm;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;

@Data
public class TotalSeeds extends Domain {

    private String save_point;

    private String farm_point;

    private String Cash_point;
}
