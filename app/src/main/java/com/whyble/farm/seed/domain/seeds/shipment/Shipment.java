package com.whyble.farm.seed.domain.seeds.shipment;

import com.whyble.farm.seed.domain.Domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shipment extends Domain {

    private String cash_point;

    private String signdate;
    
}
