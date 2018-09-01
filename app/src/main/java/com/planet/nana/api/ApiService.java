package com.planet.nana.api;

import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/test")
    Response<Void> testApi();

}
