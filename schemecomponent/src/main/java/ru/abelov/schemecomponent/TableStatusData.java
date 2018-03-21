package ru.abelov.schemecomponent;


import android.app.AlarmManager;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.abelov.schemecomponent.entity.Hour;
import ru.abelov.schemecomponent.entity.Store;
import ru.abelov.schemecomponent.entity.TableEntity;
import ru.abelov.schemecomponent.entity.TableStatusesEntity;


/**
 * Created by artem on 13.07.17.
 */

public class TableStatusData {

    private long orderStop;
    private long orderStart;
    private long openTime;
    private long closeTime;
    private long delay;
    private long interval;
    private int politics;

    private TableEntity selectedTable;
    private long currentDate;
    private Store mStore;
    private Context context;
    private long duration;


    public TableStatusData(Context context, long currentDate, Store mStore, long orderStart, long interval, long delay, int politics) {
        this.context = context;
        this.currentDate = currentDate;
        this.mStore = mStore;
        this.delay = delay;
        openTime = getWorkingTimeToday(mStore.orderBegin, context.getResources().getString(R.string.working_time_format), 1000 * 60 * 60 * 8);
        closeTime = getWorkingTimeToday(mStore.orderEnd, context.getResources().getString(R.string.working_time_format), 1000 * 60 * 60 * 23);
        this.orderStart = orderStart;
        this.interval = interval;
        this.politics = politics;
        this.orderStart = openTime + this.interval * (Math.round((this.orderStart - openTime) / this.interval));

        if(closeTime <= openTime){
            closeTime = closeTime + AlarmManager.INTERVAL_DAY;
        }

        while (((this.orderStart < Calendar.getInstance().getTimeInMillis() + this.delay)
                || (this.orderStart < openTime)) && (this.orderStart <= closeTime - this.interval)) {
            this.orderStart = this.orderStart + this.interval;
        }


        this.orderStop = this.orderStart + this.interval * 2;

        this.orderStop = openTime + this.interval * (Math.round((this.orderStop - openTime) / this.interval) /*+ interval*/);
        if (this.orderStop > closeTime) {
            this.orderStop = closeTime;
        }
        duration = orderStop - this.orderStart;
    }

    public long getOrderStop() {
        return orderStop;
    }

    public void setOrderStop(long orderStop) {
        this.orderStop = orderStop;
    }

    public long getOrderStart() {
        return orderStart;
    }

    public Date getDateOrderStart() {
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(orderStart);
        return mCal.getTime();
    }

    public Date getDateOrderStop() {
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(orderStop);
        return mCal.getTime();
    }

    public void setOrderStart(long orderStart) {
        this.orderStart = orderStart;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean onPeriodClick(long time) {
        if (time < openTime || time > closeTime || time < Calendar.getInstance().getTimeInMillis() + delay || time > closeTime - interval) {
            return false;
        }
        orderStart = time;
        orderStop = orderStart + duration;
        if (orderStop > closeTime) {
            orderStop = closeTime;
        }

        return true;
    }



    public List<Hour> generateTimeLine() {
        Calendar openDateTime;
        Calendar closeDateTime;
        if(closeTime <= openTime){
            closeTime = closeTime + AlarmManager.INTERVAL_DAY;
        }
        List<Hour> result = new ArrayList<>();
        Calendar open = (Calendar) Calendar.getInstance().clone();
        open.setTimeInMillis(openTime);
        if (openTime == 0) {
            open.set(Calendar.HOUR, 0);
            open.set(Calendar.MINUTE, 0);
            open.set(Calendar.SECOND, 0);
            open.set(Calendar.MILLISECOND, 0);
        }
        Calendar close = (Calendar) Calendar.getInstance().clone();
        close.setTimeInMillis(closeTime);
        if (closeTime == 0) {
            close.set(Calendar.HOUR, 0);
            close.set(Calendar.MINUTE, 0);
            close.set(Calendar.SECOND, 0);
            close.set(Calendar.MILLISECOND, 0);
        }

//        openDateTime = Calendar.getInstance();
//        openDateTime.setTimeInMillis(currentDate);
//        openDateTime.set(Calendar.HOUR_OF_DAY, open.get(Calendar.HOUR_OF_DAY));
//        openDateTime.set(Calendar.MINUTE, open.get(Calendar.MINUTE));
//        openDateTime.set(Calendar.SECOND, open.get(Calendar.SECOND));
//        openDateTime.set(Calendar.MILLISECOND, open.get(Calendar.MILLISECOND));
//
//        closeDateTime = Calendar.getInstance();
//        closeDateTime.setTimeInMillis(currentDate);
//        closeDateTime.set(Calendar.HOUR_OF_DAY, close.get(Calendar.HOUR_OF_DAY));
//        closeDateTime.set(Calendar.MINUTE, close.get(Calendar.MINUTE));
//        closeDateTime.set(Calendar.SECOND, close.get(Calendar.SECOND));
//        closeDateTime.set(Calendar.MILLISECOND, close.get(Calendar.MILLISECOND));

        int h = 0;


        long d = open.getTimeInMillis() + (interval * h++);

        while (d <= close.getTimeInMillis()) {
            result.add(new Hour(d));
            d = open.getTimeInMillis() + (interval * h++);
        }
        return result;
    }

    public boolean isBuzy(TableEntity table) {
        if (table.tableStatuses != null) {
            for (TableStatusesEntity status : table.tableStatuses) {
                if (!(status.orderBegin >= orderStop || status.orderEnd <= orderStart)/*|| status.available == 1*/) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMyBookingOrder(TableEntity table) {
        if (table.tableStatuses != null) {
            for (TableStatusesEntity status : table.tableStatuses) {
                if (status.orderBegin < orderStop && status.orderEnd > orderStart
                        && status.userId == 321/*user.id*/)/*|| status.available == 1*/ {
                    return true;
                }
            }
        }
        return false;
    }

    public long getWorkingTimeToday(String openTime, String timeFormat, long defaultTime) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.getDefault());
        Calendar dateTime = (Calendar) Calendar.getInstance().clone();
        try {
            dateTime.setTime(format.parse(openTime));
        } catch (ParseException e) {
            dateTime.setTimeInMillis(defaultTime);
        }

        Calendar current = (Calendar) Calendar.getInstance().clone();
        current.setTimeInMillis(currentDate);
        current.set(Calendar.HOUR_OF_DAY, dateTime.get(Calendar.HOUR_OF_DAY));
        current.set(Calendar.MINUTE, dateTime.get(Calendar.MINUTE));
        current.set(Calendar.SECOND, dateTime.get(Calendar.SECOND));
        current.set(Calendar.MILLISECOND, dateTime.get(Calendar.MILLISECOND));
        return current.getTimeInMillis();
    }

    public int getGray(long time) {
        if (time < openTime || time < Calendar.getInstance().getTimeInMillis() + delay) {
            return 0b11;
        }
        if (time < openTime + interval || time < Calendar.getInstance().getTimeInMillis() + delay + interval) {
            return 0b10;
        }

        if (time >= closeTime + interval) {
            return 0b11;
        }
        if (time >= closeTime) {
            return 0b01;
        }
        return 0b00;

    }

    public int getOrange(long time) {

        if (time > orderStart && time < orderStop) {
            return 0b11;
        }
        if (time > orderStop) {
            return 0b00;
        }
        if (time == orderStart) {
            return 0b01;
        }
        if (time == orderStop) {
            return 0b10;
        }
        return 0b00;
    }

    public int getVacant(long time) {
        int buzy = 0b00;

        if (selectedTable != null && selectedTable.tableStatuses != null) {
            for (TableStatusesEntity status : selectedTable.tableStatuses) {
                if (status.orderBegin < time && status.orderEnd > time) {
                    buzy = buzy | 0b11;
                }
                if (status.orderBegin == time) {
                    buzy = buzy | 0b01;
                }
                if (status.orderEnd == time) {
                    buzy = buzy | 0b10;
                }
            }
        }

        return buzy;
    }

    public void reset(long currentDate, long orderStart) {
        selectedTable = null;
        this.currentDate = currentDate;
        this.orderStart = orderStart;

        openTime = getWorkingTimeToday(mStore.orderBegin, context.getResources().getString(R.string.working_time_format), 1000 * 60 * 60 * 8);
        closeTime = getWorkingTimeToday(mStore.orderEnd, context.getResources().getString(R.string.working_time_format), 1000 * 60 * 60 * 23);

        this.orderStart = openTime + this.interval * (Math.round((this.orderStart - openTime) / this.interval));

        while (((this.orderStart < Calendar.getInstance().getTimeInMillis() + this.delay) || (this.orderStart < openTime)) && (this.orderStart <= closeTime - this.interval)) {
            this.orderStart = this.orderStart + this.interval;

        }

        this.orderStop = this.orderStart + duration;

        this.orderStop = openTime + this.interval * (Math.round((this.orderStop - openTime) / this.interval) /*+ interval*/);
        if (this.orderStop > closeTime) {
            this.orderStop = closeTime;
        }

    }

    public TableEntity getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(TableEntity selectedTable) {
        this.selectedTable = selectedTable;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public boolean changeDuration(int delta) {
        if (orderStop + interval * delta > closeTime) {
            return false;
        }
        if (orderStop + interval * delta - orderStart < interval) {
            return false;
        }

        orderStop = orderStop + interval * delta;
        duration = orderStop - orderStart;
        return true;
    }

}
