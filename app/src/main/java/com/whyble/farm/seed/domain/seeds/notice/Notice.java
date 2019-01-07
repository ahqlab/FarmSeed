package com.whyble.farm.seed.domain.seeds.notice;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Notice extends Domain {

    private String Title;
}
