package com.planet.nana.api;

import com.planet.nana.model.Zone;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("/zone/{user_id}")
    Single<ArrayList<Zone>> getZoneList(
            @Path("user_id") String userId
    );

}
