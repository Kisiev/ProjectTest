package com.example.a1.projecttest.entities;

import com.example.a1.projecttest.AppDatabase;
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
    private String _id;
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
    @Column()
    private String completion;
    @Column()
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

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

    public static void insertIn(String _id, String scheduleId, String statusId, String userId, String name, String scheduleName, String comment, String completion, String imageUrl){
        SQLite.insert(FeedEntity.class).columns("_id", "scheduleId", "statusId", "userId", "name", "scheduleName", "comment", "completion", "imageUrl")
                .values(_id, scheduleId, statusId, userId, name, scheduleName, comment, completion, imageUrl)
                .execute();
    }

    public static void deleteAll(){
        SQLite.delete().from(FeedEntity.class).execute();
    }
}
