package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetServiceListModel;
import com.example.a1.projecttest.rest.Models.GetServiceType;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.Models.GetUserData;

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
    Call<String> setCoordinates (@Query("id") int id,
                               @Query("coordinateX") String coordinateX,
                               @Query("coordinateY") String coordinateY);

    @GET("/api/getServiceTypesByServiceListId.php")
    Call<List<GetServiceType>> getServiceTypeCall (@Query("id") String id);

    @GET("/api/emailRegistration.php")
    Call<String> getPassByEmail (@Query("email") String email);

    @GET("/api/signing_in.php")
    Call<GetUserData> getUserData (@Query("email") String email,
                                   @Query("password") String password);
    @POST("/api/activation.php")
    Call<GetStatusCode> setUserData (@Query("id") String id,
                                     @Query("patronymic") String patronymic,
                                     @Query("surname") String surname,
                                     @Query("name") String name,
                                     @Query("roleId") String role);

    @GET("/api/getUserByRoleId.php")
    Call<List<GetUserData>> getUsersByRole (@Query("roleId") String id);

    @GET("/api/getScheduleByGroupId.php")
    Call<List<GetScheduleListModel>> getSchedule (@Query("groupId") String id);

    @GET("/api/getServiceList.php")
    Call<List<GetServiceListModel>> getServiceList();

    @GET("/api/getServicesByServiceTypeId.php")
    Call<List<GetServicesByServiceTypeModel>> getServicesByServiceType(@Query("serviceTypeId") String id);

    @GET("/api/createSchedule.php")
    Call<GetStatusCode> setSchedule(@Query("serviceId") String serviceId,
                                    @Query("day") String day,
                                    @Query("timeFrom") String timeFrom,
                                    @Query("timeTo") String timeTo,
                                    @Query("groupId") String groupId);

    @GET ("/api/updateScheduleById.php")
    Call<GetStatusCode> updateSchedule(@Query("id") String id,
                                       @Query("serviceId") String serviceId,
                                       @Query("day") String day,
                                       @Query("timeFrom") String timeFrom,
                                       @Query("timeTo") String timeTo,
                                       @Query("groupId") String groupId);

    @GET ("/api/deleteScheduleById.php")
    Call<GetStatusCode> deleteSchedule(@Query("id") String id);
}