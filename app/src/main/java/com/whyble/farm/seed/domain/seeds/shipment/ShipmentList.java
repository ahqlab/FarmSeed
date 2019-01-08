package com.whyble.farm.seed.domain.seeds.shipment;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentList {

    private String cash;

    private List<Shipment> cash_list;
}
