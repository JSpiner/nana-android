package com.planet.nana.api;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Single<Response<Void>> login(
            @Field("email") String email,
            @Field("password") String password
    );

}
