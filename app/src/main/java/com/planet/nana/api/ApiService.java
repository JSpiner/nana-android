package com.planet.nana.api;

import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Completable login(
            @Field("email") String email,
            @Field("password") String password
    );

}
