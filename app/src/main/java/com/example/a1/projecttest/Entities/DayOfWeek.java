package com.example.a1.projecttest.Entities;


import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

@Table(database = AppDatabase.class)
public class DayOfWeek {
    @PrimaryKey
    private int id;

    @Column
    private String day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public static List<DayOfWeek> selectDays(){
        return SQLite.select().from(DayOfWeek.class)
                .queryList();
    }

    public static void insertDay(String day){
        SQLite.insert(DayOfWeek.class).columns("day").values(day).execute();
    }
}
