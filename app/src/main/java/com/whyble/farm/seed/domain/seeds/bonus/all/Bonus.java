package com.whyble.farm.seed.domain.seeds.bonus.all;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Bonus extends Domain {

    private String bouns_point;

    private String signdate;
}
