package com.whyble.farm.seed.domain.seeds.cash.all;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;

@Data
public class CashList extends Domain {

    private String seed;

    private List<Cash> seed_list;
}