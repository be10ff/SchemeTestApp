package ru.abelov.schemeTimeComponent.entity;

import java.util.List;

/**
 * Created by artem on 22.03.18.
 */

public abstract class ITable {
    public abstract long getId();
    public abstract int getX();
    public abstract int getY();
    public abstract int getCapacity();
    public abstract String getImageUrl();
    abstract public List<IStatus> getStatuses();
}
