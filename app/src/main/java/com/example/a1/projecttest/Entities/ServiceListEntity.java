package com.example.a1.projecttest.Entities;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


@Table(database = AppDatabase.class)
public class ServiceListEntity extends BaseModel {
    @PrimaryKey
    private int id;

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

    public static List<ServiceListEntity> select(){
        return SQLite.select().from(ServiceListEntity.class)
                .queryList();
    }

    public static void insertUpbring(String name){
        SQLite.insert(ServiceListEntity.class)
                .columns("name")
                .values(name)
                .execute();
    }
}