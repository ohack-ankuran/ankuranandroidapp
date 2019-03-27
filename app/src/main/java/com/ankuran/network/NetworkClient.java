package com.ankuran.network;

import com.ankuran.model.ActivityDetails;
import com.ankuran.model.Employee;
import com.ankuran.model.Item;
import com.ankuran.model.Settlement;
import com.ankuran.model.dao.GroupWage;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkClient {

    //TODo add  ankuran segment
    String SEGMENT_ANKURAN_APP = "";
    String SEGMENT_TODOS = "/todos";
    String SEGMENT_POSTS = "/posts";


    String SEGMENT_CENTER="/centres/1";
    String SEGMENT_EMPLOYEES="/employees/";
    String SEGMENT_GROUP_WAGES="activities";
    String SEGMENT_ACTIVITIES="/activities";


    String SEGMENT_CATALOGUE=" /catalogue";
    String SEGMENT_PRRODUCT="/products";
    String SEGMENT_PAYOUT_ACTIVITY="/payment-activities";

    String SEGMENT_SETTLEMENT="/settlements";


    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_SETTLEMENT)
    Call<JsonObject> addSettlement(@Body Settlement settlement);



    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+SEGMENT_PAYOUT_ACTIVITY)
    Call<JsonObject> allPayout(@Query("lowerTimeCreated") String lowerTimeCreated,@Query("upperTimeCreated") String upperTimeCreated);



//
//    @GET(SEGMENT_ANKURAN_APP +SEGMENT_TODOS+ "/{todoId}")
//    Call<JsonObject> todos(@Path("todoId") String todoId);
//
//    @POST(SEGMENT_ANKURAN_APP + SEGMENT_POSTS)
//    Call<JsonObject> posts(@Body JsonObject data);

    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CATALOGUE+SEGMENT_PRRODUCT)
    Call<JsonObject> allAllProducts();

    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CATALOGUE+SEGMENT_PRRODUCT)
    Call<JsonObject> addItem(@Body Item item);

    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES)
    Call<JsonObject> allEmployee();

    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_SETTLEMENT)
    Call<JsonObject> allSettlement();

    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES)
    Call<JsonObject> addEmployee(@Body Employee employee);


    @PATCH(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+"{employeeId}")
    Call<JsonObject> updateEmployee(@Path("employeeId") long employeeId,@Body Employee employee);

    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+SEGMENT_GROUP_WAGES)
    Call<JsonObject> saveGroupWages(@Body GroupWage groupWage);

    @POST(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+"{employeeId}"+SEGMENT_ACTIVITIES)
    Call<JsonObject> addActivity(@Path("employeeId") long employeeId,@Body ActivityDetails activityDetails);


    @GET(SEGMENT_ANKURAN_APP +SEGMENT_CENTER+SEGMENT_EMPLOYEES+"{employeeId}"+SEGMENT_ACTIVITIES)
    Call<JsonObject> getAllActivities(@Path("employeeId") long employeeId,@Query("lowerTimeCreated") String lowerTimeCreated,@Query("upperTimeCreated") String upperTimeCreated,@Query("types") String types);
}
