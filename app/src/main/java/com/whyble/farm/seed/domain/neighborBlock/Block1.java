package com.whyble.farm.seed.domain.neighborBlock;

import com.whyble.farm.seed.domain.Domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Block1 extends Domain {

    private String user_id;

    private String user_count;
}
