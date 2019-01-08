package com.whyble.farm.seed.domain.seeds.farm.all;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Farm extends Domain {

    private String farm_point;

    private String signdate;
}
