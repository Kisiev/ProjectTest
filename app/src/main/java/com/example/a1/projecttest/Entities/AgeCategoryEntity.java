package com.example.a1.projecttest.Entities;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class AgeCategoryEntity extends BaseModel {

    @PrimaryKey
    private int id;

    @Column
    private String nameAgeCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAgeCategory() {
        return nameAgeCategory;
    }

    public void setNameAgeCategory(String nameAgeCategory) {
        this.nameAgeCategory = nameAgeCategory;
    }

    public static List<AgeCategoryEntity> select(){
        return SQLite.select()
                .from(AgeCategoryEntity.class)
                .queryList();
    }

    public static void insertCategory(String name){
        SQLite.insert(AgeCategoryEntity.class)
                .columns("name")
                .values(name)
                .execute();
    }
}
