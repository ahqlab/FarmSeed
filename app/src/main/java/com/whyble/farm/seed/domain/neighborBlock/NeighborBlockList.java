package com.whyble.farm.seed.domain.neighborBlock;

import com.whyble.farm.seed.domain.Domain;

import java.util.List;

import lombok.Data;

@Data
public class NeighborBlockList extends Domain {

    private String total;

    private List<Block1> block1;

    private List<Block2> block2;

}
