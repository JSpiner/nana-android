package com.planet.nana.api;

import java.util.List;

import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    Completable login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/contact")
    Completable addContact(
            @Field("user_id") String userId,
            @Field("contact") List<String> contactList
    );

}
