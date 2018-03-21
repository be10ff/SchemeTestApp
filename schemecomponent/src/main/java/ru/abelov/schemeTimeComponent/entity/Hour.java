package ru.abelov.schemeTimeComponent.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by artem on 05.07.17.
 */

public class Hour {
    private Calendar date;
    private long time;
    private int buzy = 0;

    public Hour(long time) {
        this.time = time;
        this.date = (Calendar) Calendar.getInstance().clone();
        date.setTimeInMillis(time);
    }

    public String getTime() {
        String timeFormatShort = "H:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatShort, Locale.getDefault());
        return timeFormat.format(date.getTime());
    }

    public String getDay() {
        String dateFormatShort = "dd MMM";
        SimpleDateFormat timeFormat = new SimpleDateFormat(dateFormatShort, Locale.getDefault());
        return timeFormat.format(date.getTime());
    }

    public long getMillis() {
        return time;
    }


    public int getBuzy() {
        return buzy;
    }

    public void setBuzy(int buzy) {
        this.buzy = buzy;
    }

}
