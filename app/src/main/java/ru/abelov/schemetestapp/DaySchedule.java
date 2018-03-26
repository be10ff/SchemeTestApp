package ru.abelov.schemetestapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.abelov.schemeTimeComponent.entity.IBreak;
import ru.abelov.schemeTimeComponent.entity.IDaySchedule;

public class DaySchedule implements Serializable, IDaySchedule {
    public String dayOfWeek;
    public boolean isWork;
    public String orderBegin;
    public String orderEnd;
    public List<Breaks> breaks;

    public DaySchedule() {
        //
    }

    @Override
    public boolean getIsWork() {
        return isWork;
    }

    @Override
    public String dayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public String getOrderBegin() {
        return orderBegin;
    }

    @Override
    public String getOrderEnd() {
        return orderEnd;
    }

    @Override
    public List<IBreak> getBreaks() {
        if(breaks != null){
            List<IBreak> list = new ArrayList<>();
            for(Breaks day : breaks){
                list.add(day);
            }
            return list;
        }
        return null;
    }
}
