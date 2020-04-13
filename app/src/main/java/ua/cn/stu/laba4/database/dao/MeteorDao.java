package ua.cn.stu.laba4.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ua.cn.stu.laba4.database.models.Meteor;

@Dao
public interface MeteorDao {
    @Query("SELECT * FROM meteor")
    List<Meteor> getAll();

    @Insert
    void insert( List<Meteor> meteors);

    @Update
    void update(Meteor meteor);

    @Delete
    void delete(Meteor meteor);

    @Query("SELECT * FROM meteor WHERE id = :id")
    Meteor findById(int id);

    @Query("DELETE FROM meteor")
    void deleteAll();
}
