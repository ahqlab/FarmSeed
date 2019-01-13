package com.whyble.farm.seed.domain.gift.giftSeed;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Cash extends Domain {

    private Cash2 Cash;
}
