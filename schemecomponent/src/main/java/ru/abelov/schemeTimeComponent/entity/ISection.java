package ru.abelov.schemeTimeComponent.entity;

import java.util.List;

/**
 * Created by artem on 22.03.18.
 */

public interface ISection {
    String getSectionURL();
    List<ITable> getTables();
}
