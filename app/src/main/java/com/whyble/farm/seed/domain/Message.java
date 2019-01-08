package com.whyble.farm.seed.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Message extends Domain{

    private String Title;
}
