package com.whyble.farm.seed.domain.gift.list;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TotalGift extends Domain {

    private String plus;

    private String minus;
}
