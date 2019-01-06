package com.whyble.farm.seed.domain.seeds.save;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;

@Data
public class SaveList extends Domain {

    private String save_point;

    private String signdate;
}
