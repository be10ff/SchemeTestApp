package ru.abelov.schemetestapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.abelov.schemeTimeComponent.entity.IDaySchedule;
import ru.abelov.schemeTimeComponent.entity.IWeekSchedule;

public class WeekSchedule implements Serializable, IWeekSchedule {
    private List<DaySchedule> days;

    public WeekSchedule() {
        //
    }

    public List<DaySchedule> getDays() {
        return days;
    }

    public void setDays(List<DaySchedule> days) {
        this.days = days;
    }

    @Override
    public List<IDaySchedule> getSchedule() {
        if(days != null){
            List<IDaySchedule> list = new ArrayList<>();
            for(DaySchedule day : days){
                list.add(day);
            }
            return list;
        }
        return null;
    }
}
