package com.example.a1.projecttest.Entities;


import android.net.Uri;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class ChildEntity extends BaseModel{
    @PrimaryKey
    private int id;

    @Column
    private String name;

    @Column
    private String photo;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static List<ChildEntity> selectChild(){
        return SQLite.select().from(ChildEntity.class).queryList();
    }

    public static void insertChild(String name, String photo){
        SQLite.insert(ChildEntity.class).columns("name", "photo")
                .values(name, photo)
                .execute();
    }

    public static void deleteChild(int id){
        SQLite.delete().from(ChildEntity.class)
                .where(ChildStatusEntity_Table.id.eq(id))
                .execute();
    }
}
