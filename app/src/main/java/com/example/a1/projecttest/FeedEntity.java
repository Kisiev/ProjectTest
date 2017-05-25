package com.example.a1.projecttest;

import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class FeedEntity extends BaseModel {
    @PrimaryKey
    private long id;
    @Column()
    private String scheduleId;
    @Column()
    private String statusId;
    @Column()
    private String userId;
    @Column()
    private String name;
    @Column()
    private String scheduleName;
    @Column()
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static List<FeedEntity> selectAllNotification(){
        return SQLite.select().from(FeedEntity.class).queryList();
    }

    public static void insertIn(String scheduleId, String statusId, String userId, String name, String scheduleName, String comment){
        SQLite.insert(FeedEntity.class).columns("scheduleId", "statusId", "userId", "name", "scheduleName", "comment")
                .values(scheduleId, statusId, userId, name, scheduleName, comment)
                .execute();
    }
}
