package com.example.a1.projecttest.rest.Models;


import com.google.firebase.storage.StorageException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKinderGartenGroup {

    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("tutorName")
    @Expose
    private String tutorName;
    @SerializedName("tutorSurname")
    @Expose
    private String tutorSurname;
    @SerializedName("tutorPatronymic")
    @Expose
    private String tutorPatronymic;

    public String getTutorPatronymic() {
        return tutorPatronymic;
    }

    public void setTutorPatronymic(String tutorPatronymic) {
        this.tutorPatronymic = tutorPatronymic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorSurname() {
        return tutorSurname;
    }

    public void setTutorSurname(String tutorSurname) {
        this.tutorSurname = tutorSurname;
    }

}