package com.planet.nana.api;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String SERVER_URL = "https://test.nana.com/";
    private static ApiService apiService;

    public static ApiService getInstance() {
        if (apiService == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

}
