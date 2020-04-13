package ua.cn.stu.laba4.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.io.Serializable;

import ua.cn.stu.laba4.database.dao.MeteorDao;
import ua.cn.stu.laba4.database.models.Meteor;

@Database(entities = {Meteor.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MeteorDao getMeteorDao();
}