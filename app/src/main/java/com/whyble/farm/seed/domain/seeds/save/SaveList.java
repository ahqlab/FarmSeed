package com.whyble.farm.seed.domain.seeds.save;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveList extends Domain {

    private String save_point;

    private String signdate;
}
