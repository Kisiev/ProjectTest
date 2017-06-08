package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAttendanceModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("isPresent")
    @Expose
    private String isPresent;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("patronymic")
    @Expose
    private String patronymic;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("isActivated")
    @Expose
    private String isActivated;
    @SerializedName("groupId")
    @Expose
    private String groupId;

    public GetAttendanceModel (){}
    public GetAttendanceModel(GetAttendanceModel getAttendanceModel) {
        this.name = getAttendanceModel.getName();
        this.surname = getAttendanceModel.getSurname();
        this.patronymic = getAttendanceModel.getPatronymic();
        this.time = getAttendanceModel.getTime();
        this.date = getAttendanceModel.getDate();
        this.groupId = getAttendanceModel.getGroupId();
        this.isPresent = getAttendanceModel.getIsPresent();
        this.userId = getAttendanceModel.getUserId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}

