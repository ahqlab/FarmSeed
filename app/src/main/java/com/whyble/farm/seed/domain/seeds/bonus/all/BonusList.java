package com.whyble.farm.seed.domain.seeds.bonus.all;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BonusList extends Domain {

    private String bonus;

    private List<Bonus> bouns_list;
}
