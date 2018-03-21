package ru.abelov.schemeTimeComponent.timepicker;


import ru.abelov.schemeTimeComponent.entity.TableEntity;

public interface DatePickerListener {
    void onTimeChanged();

    void onTimeSelected(long start);

    void onOrder(long orderStart, long orderStop, TableEntity table);
}