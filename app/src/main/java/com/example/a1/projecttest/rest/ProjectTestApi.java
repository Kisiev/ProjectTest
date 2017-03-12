package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetListUsers;

import java.util.List;
import retrofit2.Call;
        import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ProjectTestApi {
    @GET("/api/GetUserById.php")
    Call<GetListUsers> getListModel (@Query("id") String id);

    @POST("/api/UpdateUserCoordinatesById.php")
    Call<String> setCoordinates (@Query("id") String id,
                               @Query("coordinateX") String coordinateX,
                               @Query("coordinateY") String coordinateY);
}