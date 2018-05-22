package ru.abelov.schemeTimeComponent.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.abelov.schemeTimeComponent.room.entity.DayScheduleEntity;

/**
 * Created by artem on 22.05.18.
 */

@Database(entities = {DayScheduleEntity.class}, version = 1, exportSchema = false)
public abstract class ScheduleDb extends RoomDatabase {
    public abstract DayScheduleDAO dayScheduleDAO();
}
