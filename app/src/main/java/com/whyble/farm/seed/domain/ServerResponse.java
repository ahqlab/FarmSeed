package com.whyble.farm.seed.domain;

import lombok.Data;

@Data
public class ServerResponse extends Domain{

    private String msg;

    private String result;
}
