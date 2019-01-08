package com.whyble.farm.seed.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EditUser extends Domain{

    private String id;

    private String passwd;

    private String name;

    private String birthday;

    private String sex;

    private String handphone1;

    private String handphone2;

    private String handphone3;

    private String email;

    private String zipcorde;

    private String address1;

    private String address2;

    private String bankname;

    private String banknum;

    private String recommend;

}
