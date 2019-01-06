package com.whyble.farm.seed.domain.seeds.save;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;

@Data
public class Save extends Domain {

    private String save_point;

    private String save_signdate;
}
