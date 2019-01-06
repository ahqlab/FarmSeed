package com.whyble.farm.seed.network.retrofit.service;

import com.whyble.farm.seed.domain.ServerResponse;
import com.whyble.farm.seed.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("member_ok.php")
    Call<ServerResponse> testLogin(@Query("id") String id,
                                   @Query("email") String email,
                                   @Query("passwd") String password,
                                   @Query("passwd2") String password2,
                                   @Query("tel1") String tel1,
                                   @Query("tel2") String tel2,
                                   @Query("tel3") String tel3,
                                   @Query("zipcorde") String zipcode,
                                   @Query("address") String address,
                                   @Query("address1") String address1,
                                   @Query("birthday") String birthday,
                                   @Query("bankname") String bankname,
                                   @Query("banknum") String banknum,
                                   @Query("recommend") String recommend);

    @POST("member_ok.php")
    Call<ServerResponse> testLogin(@Body User user);

}
