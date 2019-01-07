package com.whyble.farm.seed.domain.gift.list;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Gift extends Domain {

    private String f_id;

    private String pl;

    private String signdate;

    private String point;
}
