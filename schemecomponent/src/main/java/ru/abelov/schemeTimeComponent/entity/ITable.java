package ru.abelov.schemeTimeComponent.entity;

import java.util.List;

/**
 * Created by artem on 22.03.18.
 */

public interface ITable {
    long getId();
    int getX();
    int getY();
    int getCapacity();
    String getImageUrl();
    String getName();
    List<IStatus> getStatuses();
}
