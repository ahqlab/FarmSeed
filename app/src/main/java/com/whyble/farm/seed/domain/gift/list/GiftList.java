package com.whyble.farm.seed.domain.gift.list;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GiftList extends Domain {

    TotalGift pl_mi;

    List<Gift> gift_list;
}
