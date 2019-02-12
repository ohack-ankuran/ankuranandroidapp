package com.paypal.ankuran.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkClient {

    //TODo add  ankuran segment
    String SEGMENT_PICKUP_APP = "";
    String SEGMENT_TODOS = "/todos";
    @GET(SEGMENT_PICKUP_APP +SEGMENT_TODOS+ "/{todoId}")
    Call<JsonObject> todos(@Path("todoId") String todoId);
}
