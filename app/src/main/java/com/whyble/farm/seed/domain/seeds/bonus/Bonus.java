package com.whyble.farm.seed.domain.seeds.bonus;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Bonus extends Domain {

    private String bonus_point;
    private String bonus_signdate;
}
