package com.example.a1.projecttest.rest;

import android.support.annotation.NonNull;

import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetAllRegionsModel;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetCoordinatesByUserIdModel;
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

import java.io.IOException;
import java.util.List;

import retrofit2.http.Query;


public final class RestService {

    private RestClient restClient;
    public RestService(){
        restClient = new RestClient();
    }

    public GetListUsers getUserById (@NonNull String id) throws IOException{

        return  restClient.getProjectTestApiEdu()
                .getUserById(id)
                .execute().body();

    }

    public String setCoordinates (@NonNull int id, @NonNull String coordinateX, @NonNull String coordinateY) throws IOException{

        return  restClient.getProjectTestApiEdu()
                .setCoordinates(id, coordinateX, coordinateY)
                .execute().body();

    }

    public GetListUsers validUser ( @NonNull String login, @NonNull String password) throws IOException{
        return  restClient.getProjectTestApi()
                .validUser(login, password)
                .execute().body();

    }

    public List<GetServiceType> serviceType (String id) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getServiceTypeCall(id)
                .execute().body();
    }

    public String getPasswordByEmail (String email) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getPassByEmail(email)
                .execute().body();
    }

    public GetUserData getUserData (String email, String password) throws IOException {
        return restClient.getProjectTestApiEdu()
                .getUserData(email, password)
                .execute().body();
    }

    public List<GetUserData> getUsersByRole (String id) throws IOException {
        return restClient.getProjectTestApiEdu()
                .getUsersByRole(id)
                .execute().body();
    }
    public GetStatusCode getStatusCode (String id,
                                        String patronymic,
                                        String surname,
                                        String name,
                                        String role,
                                        String kindergartenId) throws IOException {
        return restClient.getProjectTestApiEdu()
                .setUserData(id,
                        patronymic,
                        surname,
                        name,
                        role,
                        kindergartenId)
                .execute().body();
    }

    public List<GetScheduleListModel> getScheduleListModel (String groupId, String dayId) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getSchedule(groupId, dayId)
                .execute().body();
    }

    public List<GetServiceListModel> getServiceList () throws IOException{
        return restClient.getProjectTestApiEdu()
                .getServiceList()
                .execute().body();
    }
    public List<GetServicesByServiceTypeModel> getServicesByServiceTypeModels (String id) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getServicesByServiceType(id)
                .execute().body();
    }

    public GetStatusCode setSchedule(String serviceId, String day, String timeFrom, String timeTo, String groupId) throws IOException {
        return restClient.getProjectTestApiEdu().setSchedule(serviceId, day, timeFrom, timeTo, groupId).execute().body();
    }

    public GetStatusCode updateSchedule(String id, String serviceId, String day, String timeFrom, String timeTo, String groupId) throws IOException {
        return restClient.getProjectTestApiEdu().updateSchedule(id, serviceId, day, timeFrom, timeTo, groupId).execute().body();
    }

    public GetStatusCode deleteScheduleById(String id) throws IOException{
        return restClient.getProjectTestApiEdu().deleteSchedule(id).execute().body();
    }

    public List<GetKinderGartenGroup> getKinderGartenGroups (String kindergartenId) throws IOException {
        return restClient.getProjectTestApiEdu().getGroups(kindergartenId).execute().body();
    }

    public GetKinderGarten getKinderGartenZav (String managerId) throws IOException{
        return restClient.getProjectTestApiEdu().getKinderGartenZav(managerId).execute().body();
    }

    public GetKinderGarten getKinderGartenTutor (String tutor) throws IOException{
        return restClient.getProjectTestApiEdu().getKinderGartenZav(tutor).execute().body();
    }

    public List<GetAllTutors> getAllTutorses (String kindergartenId) throws IOException{
        return  restClient.getProjectTestApiEdu().getAllTutors(kindergartenId).execute().body();
    }

    public List<GetAllKidsModel> getAllKidsModels (String kindergartenId) throws IOException {
        return restClient.getProjectTestApiEdu().getAlLKids(kindergartenId).execute().body();
    }

    public List<GetKidsByGroupIdModel> getKidsByGroupIdModels (String groupId) throws IOException {
        return restClient.getProjectTestApiEdu().getKidsByGroup(groupId).execute().body();
    }

    public List<GetAllRegionsModel> getAllRegionsModels () throws IOException {
        return restClient.getProjectTestApiEdu().getAllRegions().execute().body();
    }

    public List<GetAllRegionsModel> getAllcitiesByRegion(String code)throws IOException{
        return restClient.getProjectTestApiEdu().getAllCitiesByRegionCode(code).execute().body();
    }

    public List<GetKinderGartensByCityCode> getKinderGartensByCityCodes (String cityCode) throws IOException {
        return restClient.getProjectTestApiEdu().getKinderGartenByCityCode(cityCode).execute().body();
    }

    public GetStatusCode setGroups(String name, String kinderGartenId, String tutorId) throws IOException {
        return restClient.getProjectTestApiEdu().setGroup(name, kinderGartenId, tutorId).execute().body();
    }

    public GetStatusCode setKidInGroup(String parentId,
                                       String name,
                                       String surname,
                                       String patronymic,
                                       String groupId) throws IOException{
        return restClient.getProjectTestApiEdu().addKidInGroup(parentId, name, surname, patronymic, groupId).execute().body();
    }

    public List<GetAllKidsModel> getKidByParentId(String parentId)throws IOException{
        return restClient.getProjectTestApiEdu().getKidByParentId(parentId).execute().body();
    }

    public GetGroupByTutorModel getGroupByTutorModel (String tutorId) throws IOException{
        return restClient.getProjectTestApiEdu().getTutorGroup(tutorId).execute().body();
    }

    public GetScheduleByKidIdModel getScheduleByKidIdModels (String kidId) throws IOException {
        return restClient.getProjectTestApiEdu().getScheduleByKidId(kidId).execute().body();
    }

    public List<GetAllDaysModel> getAllDaysModels () throws IOException {
        return restClient.getProjectTestApiEdu().getAllDay().execute().body();
    }

    public GetCoordinatesByUserIdModel getCoordinatesByUserIdModel (String userId) throws IOException {
        return restClient.getProjectTestApiEdu().getCoordinates(userId).execute().body();
    }
}