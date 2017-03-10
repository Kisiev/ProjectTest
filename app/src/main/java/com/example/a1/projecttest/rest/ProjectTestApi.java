package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetListUsers;

import java.util.List;
import retrofit2.Call;
        import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ProjectTestApi {
    @GET("/api/GetAllUsers.php")
    Call<List<GetListUsers>> getListModel ();
}