package com.whyble.farm.seed.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User {

    public User(){

    }

    public User(String name ,String id, String email, String passwd, String password2, String tel1, String tel2, String tel3, String zipcode, String address, String address1, String birthday, String bankname, String banknum, String recommend) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.passwd2 = password2;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.zipcorde = zipcode;
        this.address = address;
        this.address1 = address1;
        this.birthday = birthday;
        this.bankname = bankname;
        this.banknum = banknum;
        this.recommend = recommend;
    }

    private String name;

    private String id;

    private String email;

    private String passwd;

    private String passwd2;

    private String tel1;

    private String tel2;

    private String tel3;

    private String zipcorde;

    private String address;

    private String address1;

    private String birthday;

    private String bankname;

    private String banknum;

    private String recommend;


}
