package com.example.a1.projecttest.rest;


import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetAllRegionsModel;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.example.a1.projecttest.rest.Models.GetCoordinatesByUserIdModel;
import com.example.a1.projecttest.rest.Models.GetGroupByTutorModel;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetKinderGartensByCityCode;
import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetNotificationAddToParent;
import com.example.a1.projecttest.rest.Models.GetScheduleByKidIdModel;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetScheduleStatusesByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetServiceListModel;
import com.example.a1.projecttest.rest.Models.GetServiceType;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.Models.GetStatusUploadModel;
import com.example.a1.projecttest.rest.Models.GetUserData;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
        import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

public interface ProjectTestApi {
    @GET("/api/getUserById.php")
    Call<GetListUsers> getUserById (@Query("id") String id);

    @GET("/api/Authentication.php")
    Call<GetListUsers> validUser (@Query("login") String login,
                                  @Query("password") String password);

    @POST("/api/updateCoordinatesByUserId.php")
    Call<String> setCoordinates (@Query("userId") int id,
                               @Query("coordinateX") String coordinateX,
                               @Query("coordinateY") String coordinateY);

    @GET("/api/getServiceTypesByServiceListId.php")
    Call<List<GetServiceType>> getServiceTypeCall (@Query("id") String id);

    @GET("/api/emailRegistration.php")
    Call<String> getPassByEmail (@Query("email") String email);

    @GET("/api/signing_in.php")
    Observable<GetUserData> getUserData (@Query("email") String email,
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
    Observable<List<GetScheduleListModel>> getSchedule (@Query("groupId") String id,
                                                      @Query("dayId") String dayId);
    @GET("/api/getScheduleByGroupIdAndDayId.php")
    Call<List<GetScheduleListModel>> getScheduleWithoutRx (@Query("groupId") String id,
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

    @GET ("/api/getAllKidsByGroupId.php")
    Observable<List<GetKidsByGroupIdModel>> getKidsByGroupObserver (@Query("groupId") String groupId);

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
    Observable<List<GetAllKidsModel>> getKidByParentId(@Query("parentId") String parentId);

    @GET("/api/getKindergartenGroupByTutorId.php")
    Call<GetGroupByTutorModel> getTutorGroup(@Query("tutorId") String tutorId);

    @GET("/api/getGroupIdByKidId.php")
    Call<GetScheduleByKidIdModel> getScheduleByKidId(@Query("KidId") String kid);

    @GET ("/api/getAllDays.php")
    Call<List<GetAllDaysModel>> getAllDay();

    @GET ("/api/getCoordinatesByUserId.php")
    Call<GetCoordinatesByUserIdModel> getCoordinates (@Query("userId") String id);

    @GET ("/api/getScheduleStatusesByKidId.php")
    Observable<List<GetStatusKidModel>> getStatusKid(@Query("KidId") String kidId);

    @GET ("/api/getAllScheduleStatusesByKidId.php")
    Call<List<GetStatusKidModel>> getStatusKidForFeed(@Query("KidId") String kidId);

    @GET ("/api/createScheduleStatus.php")
    Call<GetStatusCode> setStatusForKid(@Query("scheduleId") String scheduleId,
                                        @Query("statusId") String statusId,
                                        @Query("userId") String userId,
                                        @Query("comment") String comment,
                                        @Query("completion") String data);

    @GET ("/api/getScheduleStatusesByGroupIdAndScheduleId.php")
    Call<List<GetScheduleStatusesByGroupIdModel>> getGroupStatuses(@Query("groupId") String groupId,
                                                                   @Query("scheduleId") String schedule);

    @GET("/api/getAttendanceByGroupId.php")
    Observable<List<GetAttendanceModel>> getAttendance (@Query("groupId") String groupId,
                                                        @Query("date") String date);

    @GET("/api/createAttendance.php")
    Observable<GetStatusCode> createAttendance (@Query("userId") String userId,
                                                        @Query("isPresent") String isPresent);
    @POST("/api/addKidToParent.php")
    Observable<String> addKidToParent (@Query("kidId") String kidId,
                                       @Query("parentId") String parentId);

    @GET("/api/getNotificationsAddKidToParent.php")
    Observable<List<GetNotificationAddToParent>> getNotificationAddToParent (@Query("kidId") String kidId);

    @POST("/api/addKidToParentFunction.php")
    Observable<String> acceptToAddKid (@Query("url") String kidId);

    @GET ("/api/getAllTutorsByKindergartenId.php")
    Observable<List<GetAllTutors>> getAllTutorsObserver (@Query("kindergartenId") String kindergartenId);

    @GET ("/api/getKindergartenByManagerId.php")
    Observable<GetKinderGarten> getKinderGartenZavObserver (@Query("managerId") String managerId);

    @Multipart
    @POST ("/api/upload_image.php")
    Observable<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);


}