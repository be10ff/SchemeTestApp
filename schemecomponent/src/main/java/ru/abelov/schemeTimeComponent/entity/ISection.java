package ru.abelov.schemeTimeComponent.entity;

import java.util.List;

/**
 * Created by artem on 22.03.18.
 */

public abstract class ISection {
    abstract public String getSectionURL();
    abstract public List<ITable> getTables();
}
