package com.ankuran.network;

import com.ankuran.model.Employee;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkClient {

    //TODo add  ankuran segment
    String SEGMENT_ANKURAN_APP = "";
    String SEGMENT_TODOS = "/todos";
    String SEGMENT_POSTS = "/posts";


    String SEGMENT_CENTER="/centres/1";
    String SEGMENT_EMPLOYEES="/employees/";




//
//    @GET(SEGMENT_ANKURAN_APP +SEGMENT_TODOS+ "/{todoId}")
//    Call<JsonObject> todos(@Path("todoId") String todoId);
//
//    @POST(SEGMENT_ANKURAN_APP + SEGMENT_POSTS)
//    Call<JsonObject> posts(@Body JsonObject data);



    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES)
    Call<JsonObject> allEmployee();

    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES)
    Call<JsonObject> addEmployee(@Body Employee employee);


    @PATCH(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+"{employeeId}")
    Call<JsonObject> updateEmployee(@Path("employeeId") long employeeId,@Body Employee employee);
}
