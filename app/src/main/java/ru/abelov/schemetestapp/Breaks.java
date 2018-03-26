package ru.abelov.schemetestapp;

import java.io.Serializable;

import ru.abelov.schemeTimeComponent.entity.IBreak;

public class Breaks implements Serializable, IBreak {
    public String breakBegin;
    public String breakEnd;

    public Breaks() {
        //
    }

    public Breaks(String begin, String end) {
        breakBegin = begin;
        breakEnd = end;
    }

    @Override
    public String getBreakBegin() {
        return breakBegin;
    }

    @Override
    public String getBreakEnd() {
        return breakEnd;
    }
}
