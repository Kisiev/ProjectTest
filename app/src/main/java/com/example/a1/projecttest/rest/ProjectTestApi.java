package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetAllRegionsModel;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetGroupByTutorModel;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetKinderGartensByCityCode;
import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetScheduleByKidIdModel;
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
    Call<GetListUsers> getUserById (@Query("id") String id);

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
                                     @Query("roleId") String role,
                                     @Query("kindergartenId") String kindergartenId);

    @GET("/api/getUserByRoleId.php")
    Call<List<GetUserData>> getUsersByRole (@Query("roleId") String id);

    @GET("/api/getScheduleByGroupIdAndDayId.php")
    Call<List<GetScheduleListModel>> getSchedule (@Query("groupId") String id,
                                                  @Query("dayId") String dayId);

    @GET("/api/getServiceList.php")
    Call<List<GetServiceListModel>> getServiceList();

    @GET("/api/getServicesByServiceTypeId.php")
    Call<List<GetServicesByServiceTypeModel>> getServicesByServiceType(@Query("serviceTypeId") String id);

    @GET("/api/createSchedule.php")
    Call<GetStatusCode> setSchedule(@Query("serviceId") String serviceId,
                                    @Query("dayId") String day,
                                    @Query("timeFrom") String timeFrom,
                                    @Query("timeTo") String timeTo,
                                    @Query("groupId") String groupId);

    @GET ("/api/updateScheduleById.php")
    Call<GetStatusCode> updateSchedule(@Query("id") String id,
                                       @Query("serviceId") String serviceId,
                                       @Query("dayId") String day,
                                       @Query("timeFrom") String timeFrom,
                                       @Query("timeTo") String timeTo,
                                       @Query("groupId") String groupId);

    @GET ("/api/deleteScheduleById.php")
    Call<GetStatusCode> deleteSchedule(@Query("id") String id);

    @GET ("/api/getKindergartenGroupsByKindergartenId.php")
    Call<List<GetKinderGartenGroup>> getGroups (@Query("kindergartenId") String kindergartenId);

    @GET ("/api/getKindergartenByManagerId.php")
    Call<GetKinderGarten> getKinderGartenZav (@Query("managerId") String managerId);

    @GET ("/api/getKindergartenByTutorId.php")
    Call<GetKinderGarten> getKinderGartenTutor (@Query("tutorId") String managerId);

    @GET ("/api/getAllTutorsByKindergartenId.php")
    Call<List<GetAllTutors>> getAllTutors (@Query("kindergartenId") String kindergartenId);

    @GET ("/api/getAllKidsByKindergartenId.php")
    Call<List<GetAllKidsModel>> getAlLKids (@Query("kindergartenId") String kindergartenId);

    @GET ("/api/getAllKidsByGroupId.php")
    Call<List<GetKidsByGroupIdModel>> getKidsByGroup (@Query("groupId") String groupId);

    @GET ("/api/getAllRegions.php")
    Call<List<GetAllRegionsModel>> getAllRegions ();

    @GET ("/api/GetAllCitiesByRegionCode.php")
    Call<List<GetAllRegionsModel>> getAllCitiesByRegionCode (@Query("code") String code);

    @GET ("/api/GetAllKindergartensByCityCode.php")
    Call<List<GetKinderGartensByCityCode>> getKinderGartenByCityCode(@Query("cityCode") String cityCode);

    @GET ("/api/addGroup.php")
    Call<GetStatusCode> setGroup (@Query("name") String name,
                                       @Query("kindergartenId") String kindergartenId,
                                       @Query("tutorId") String tutorId);
    @GET("/api/addKid.php")
    Call<GetStatusCode> addKidInGroup(@Query("parentId") String parentId,
                                      @Query("name") String name,
                                      @Query("surname") String surname,
                                      @Query("patronymic") String patronymic,
                                      @Query("groupId") String groupId);
    @GET("/api/getAllKidsByParentId.php")
    Call<List<GetAllKidsModel>> getKidByParentId(@Query("parentId") String parentId);

    @GET("/api/getKindergartenGroupByTutorId.php")
    Call<GetGroupByTutorModel> getTutorGroup(@Query("tutorId") String tutorId);

    @GET("/api/getGroupIdByKidId.php")
    Call<GetScheduleByKidIdModel> getScheduleByKidId(@Query("KidId") String kid);

    @GET ("/api/getAllDays.php")
    Call<List<GetAllDaysModel>> getAllDay();
}