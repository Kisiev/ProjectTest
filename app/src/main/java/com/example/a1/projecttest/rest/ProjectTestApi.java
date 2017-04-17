package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetServiceType;

import java.util.List;
import retrofit2.Call;
        import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ProjectTestApi {
    @GET("/api/getUserById.php")
    Call<GetListUsers> getListModel (@Query("id") String id);

    @GET("/api/Authentication.php")
    Call<GetListUsers> validUser (@Query("login") String login,
                                  @Query("password") String password);

    @POST("/api/updateUserCoordinatesById.php")
    Call<String> setCoordinates (@Query("id") String id,
                               @Query("coordinateX") String coordinateX,
                               @Query("coordinateY") String coordinateY);

    @GET("/api/getServiceTypesById.php")
    Call<List<GetServiceType>> getServiceTypeCall (@Query("id") String id);

    @GET("/api/emailRegistration.php")
    Call<String> getPassByEmail (@Query("email") String email);

}