package ru.abelov.schemeTimeComponent.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.abelov.schemeTimeComponent.room.entity.DayScheduleEntity;

/**
 * Created by artem on 22.05.18.
 */


@Dao
public interface DayScheduleDAO {

    @Query("SELECT * FROM DayScheduleEntity")
    List<DayScheduleEntity> getSchedule();

    @Query("SELECT * FROM DayScheduleEntity WHERE dayOfWeek =:day")
    DayScheduleEntity getByDay(String day);

    @Insert
    void insert(DayScheduleEntity entity);

    @Insert
    void update(DayScheduleEntity entity);

    @Insert
    void delete(DayScheduleEntity entity);
}
