package com.whyble.farm.seed.domain.seeds.farm.all;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;

@Data
public class FarmList extends Domain {

    private String farm;

    private List<Farm> farm_list;
}
