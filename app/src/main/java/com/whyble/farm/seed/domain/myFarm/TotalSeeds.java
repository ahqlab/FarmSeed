package com.whyble.farm.seed.domain.myFarm;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TotalSeeds extends Domain {

    private String save_point;

    private String farm_point;

    private String Cash_point;
}
