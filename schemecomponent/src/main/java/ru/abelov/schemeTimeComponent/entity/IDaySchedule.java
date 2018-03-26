package ru.abelov.schemeTimeComponent.entity;

import java.util.List;

/**
 * Created by artem on 22.03.18.
 */

public interface IDaySchedule {

    boolean getIsWork();

    String dayOfWeek();

    String getOrderBegin();
    String getOrderEnd();

    List<IBreak> getBreaks();

}
