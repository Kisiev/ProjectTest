package com.example.a1.projecttest.Entities;


import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class UpbringingEntity extends BaseModel{
    @PrimaryKey
    private int id;

    @Column
    private int serviceListId;

    @Column
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServiceListId() {
        return serviceListId;
    }

    public void setServiceListId(int serviceListId) {
        this.serviceListId = serviceListId;
    }

    public static List<UpbringingEntity> selectAll(){
        return SQLite.select().from(UpbringingEntity.class)
                .queryList();
    }

    public static void insertItem(String name, int serviceListId){
        SQLite.insert(UpbringingEntity.class)
                .columns("name", "serviceListId")
                .values(name, serviceListId)
                .execute();
    }

    public static void deleteAll(){
        SQLite.delete().from(UpbringingEntity.class).execute();
    }
}
