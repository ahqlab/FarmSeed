package com.whyble.farm.seed.domain.seeds.farm;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Farm extends Domain {

    private String farm_point;
    private String farm_signdate;

}
