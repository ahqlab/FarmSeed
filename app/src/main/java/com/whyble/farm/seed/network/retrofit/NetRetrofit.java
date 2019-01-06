package com.whyble.farm.seed.network.retrofit;

import com.whyble.farm.seed.network.retrofit.service.UserService;
import com.whyble.farm.seed.util.TextManager.TextManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {
    private static NetRetrofit ourInstance = new NetRetrofit();

    public static NetRetrofit getInstance() {
        return ourInstance;
    }

    private NetRetrofit() {

    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(TextManager.SERVER_ROOT)
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .build();

    UserService userService = retrofit.create(UserService.class);

    public UserService getUserService() {
        return userService;
    }



}

