package com.whyble.farm.seed.domain.seeds.cash.all;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CashList extends Domain {

    private String seed;

    private List<Cash> seed_list;
}
