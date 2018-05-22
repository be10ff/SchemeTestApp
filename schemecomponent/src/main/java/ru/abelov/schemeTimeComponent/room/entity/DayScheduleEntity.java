package ru.abelov.schemeTimeComponent.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.abelov.schemeTimeComponent.entity.IDaySchedule;

/**
 * Created by artem on 22.05.18.
 */

@Entity
public class DayScheduleEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String dayOfWeek;

    public String orderBegin;

    public String orderEnd;

    public DayScheduleEntity() {

    }

    public DayScheduleEntity(IDaySchedule entity) {
        this.dayOfWeek = entity.dayOfWeek();
        this.orderBegin = entity.getOrderBegin();
        this.orderEnd = entity.getOrderEnd();
    }

}
