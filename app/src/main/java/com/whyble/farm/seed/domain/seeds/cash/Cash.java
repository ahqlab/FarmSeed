package com.whyble.farm.seed.domain.seeds.cash;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Cash extends Domain {

    private String cash_point;

    private String cash_signdate;

}
