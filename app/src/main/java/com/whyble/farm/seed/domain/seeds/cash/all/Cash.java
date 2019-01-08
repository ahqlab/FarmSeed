package com.whyble.farm.seed.domain.seeds.cash.all;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Cash extends Domain {

    private String seed_point;

    private String signdate;
}
