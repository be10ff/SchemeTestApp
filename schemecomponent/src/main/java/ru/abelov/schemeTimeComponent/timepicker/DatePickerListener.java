package ru.abelov.schemeTimeComponent.timepicker;


import ru.abelov.schemeTimeComponent.entity.ITable;

public interface DatePickerListener {

    void onTimeChanged();

    void onTimeSelected(long start);

    void onOrder(long orderStart, long orderStop, ITable table);

    void onDateChanged(long start);
}