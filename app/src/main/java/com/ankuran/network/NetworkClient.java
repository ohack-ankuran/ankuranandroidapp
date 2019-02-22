package com.ankuran.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkClient {

    //TODo add  ankuran segment
    String SEGMENT_ANKURAN_APP = "";
    String SEGMENT_TODOS = "/todos";
    String SEGMENT_POSTS = "/posts";




    @GET(SEGMENT_ANKURAN_APP +SEGMENT_TODOS+ "/{todoId}")
    Call<JsonObject> todos(@Path("todoId") String todoId);

    @POST(SEGMENT_ANKURAN_APP + SEGMENT_POSTS)
    Call<JsonObject> posts(@Body JsonObject data);
}
