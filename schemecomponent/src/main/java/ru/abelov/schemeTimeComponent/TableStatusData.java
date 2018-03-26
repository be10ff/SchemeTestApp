package ru.abelov.schemeTimeComponent;


import android.app.AlarmManager;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.abelov.schemeTimeComponent.entity.Hour;
import ru.abelov.schemeTimeComponent.entity.IBreak;
import ru.abelov.schemeTimeComponent.entity.IDaySchedule;
import ru.abelov.schemeTimeComponent.entity.ITable;
import ru.abelov.schemeTimeComponent.entity.IStore;
import ru.abelov.schemeTimeComponent.entity.IStatus;
import ru.abelov.schemeTimeComponent.entity.IUser;


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

    private IUser user;

    private ITable selectedTable;
    private long currentDate;
    private IStore store;
    private Context context;
    private long duration;


    public TableStatusData(Context context, long currentDate, IStore store, IUser user, long orderStart, long interval, long delay, int politics, String timeFormat) {
        this.context = context;
        this.currentDate = currentDate;
        this.store = store;
        this.delay = delay;
        this.user = user;

        openTime = getWorkingTimeToday(getSchedule().getOrderBegin(), store.getTimeFormat(), 1000 * 60 * 60 * 8);
        closeTime = getWorkingTimeToday(getSchedule().getOrderEnd(), store.getTimeFormat(), 1000 * 60 * 60 * 23);
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

//        boolean result = false;
        if(getSchedule() != null && getSchedule().getBreaks() != null) {
            for (IBreak b : getSchedule().getBreaks()) {

                long breakBegin = getWorkingTimeToday(b.getBreakBegin(),"hhmm", 0);
                long breakEnd = getWorkingTimeToday(b.getBreakEnd(), "hhmm", 0);
                if (time >= breakBegin && time < breakEnd) {
                    int i = 0;
                    return false;
                }
            }
        }

        return true;
    }



    public List<Hour> generateTimeLine() {

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

        int h = 0;

        long d = open.getTimeInMillis() + (interval * h++);

        while (d <= close.getTimeInMillis()) {
            result.add(new Hour(d));
            d = open.getTimeInMillis() + (interval * h++);
        }
        return result;
    }

    public boolean re_isBuzy(ITable table) {
        if (table.getStatuses() != null) {
            for (IStatus status : table.getStatuses()) {
                if (!(status.getOrderBegin() >= orderStop || status.getOrderEnd() <= orderStart)/*|| status.available == 1*/) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBuzy(ITable table) {
        boolean result = false;
        if (table.getStatuses() != null) {
            for (IStatus status : table.getStatuses()) {
                if (!(status.getOrderBegin() >= orderStop || status.getOrderEnd() <= orderStart)/*|| status.available == 1*/) {
                    result = result || true;
                }
            }
        }
        if(getSchedule() != null && getSchedule().getBreaks() != null) {
            for (IBreak b : getSchedule().getBreaks()) {

                long breakBegin = getWorkingTimeToday(b.getBreakBegin(),"hhmm", 0);
                long breakEnd = getWorkingTimeToday(b.getBreakEnd(), "hhmm", 0);
                if (!(breakBegin >= orderStop || breakEnd <= orderStart)/*|| status.available == 1*/) {
                    result = result || true;
                }
            }
        }
        return result;
    }

    public boolean isMyBookingOrder(ITable table) {
        if (table.getStatuses() != null) {
            for (IStatus status : table.getStatuses()) {
                if (status.getOrderBegin() < orderStop && status.getOrderEnd() > orderStart
                        && status.getUserId() == user.getUserId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<IBreak> getBreaks(){
        List<IBreak> result = new ArrayList<>();
        if(getSchedule() != null && getSchedule().getBreaks() != null) {
            for (IBreak b : getSchedule().getBreaks()) {
                if()
                result.add(b);
            }
        }
    }

    public IDaySchedule getSchedule(){
        Calendar current = (Calendar) Calendar.getInstance().clone();
        current.setTimeInMillis(openTime);

            if (store.getSchedule() != null
                    && store.getSchedule().getSchedule() != null
                    && store.getSchedule().getSchedule().size() == 7) {

                switch (current.get(Calendar.DAY_OF_WEEK)){
                    case 1: //sunday
                        return store.getSchedule().getSchedule().get(6);
                    default:
                        return store.getSchedule().getSchedule().get(current.get(Calendar.DAY_OF_WEEK) - 2);
                }

//                return store.getSchedule().getSchedule().get(current.get(Calendar.DAY_OF_WEEK));
            } else {
                return new IDaySchedule() {
                    @Override
                    public boolean getIsWork() {
                        return true;
                    }

                    @Override
                    public String dayOfWeek() {
                        return null;
                    }

                    @Override
                    public String getOrderBegin() {
                        return store.getOrderBegin();
                    }

                    @Override
                    public String getOrderEnd() {
                        return store.getOrderEnd();
                    }

                    @Override
                    public List<IBreak> getBreaks() {
                        return null;
                    }
                };
            }
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

    public int re_getGray(long time) {
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

    public int getGray(long time) {
        int res = 0b00;

        if (time < openTime || time < Calendar.getInstance().getTimeInMillis() + delay) {
            res = res | 0b11;
        }

        if (time < openTime + interval || time < Calendar.getInstance().getTimeInMillis() + delay + interval) {
            res = res | 0b10;
        }

        if (time >= closeTime + interval) {
            res = res | 0b11;
        }

        if (time >= closeTime) {
            res = res |0b01;
        }

        if(getSchedule() != null && getSchedule().getBreaks() != null){
            for(IBreak b : getSchedule().getBreaks()){

                long breakBegin = getWorkingTimeToday(b.getBreakBegin(),"hhmm", 0);
                long breakEnd = getWorkingTimeToday(b.getBreakEnd(), "hhmm", 0);

                if (time == breakBegin) {
                    res = res |0b01;
                }

                if (time == breakEnd) {
                    res = res |0b10;
                }

                if (time < breakEnd && time > breakBegin) {
                    res = res |0b11;
                }

            }
        }

        return res;
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

        if (selectedTable != null && selectedTable.getStatuses() != null) {
            for (IStatus status : selectedTable.getStatuses()) {
                if (status.getOrderBegin() < time && status.getOrderEnd() > time) {
                    buzy = buzy | 0b11;
                }
                if (status.getOrderBegin() == time) {
                    buzy = buzy | 0b01;
                }
                if (status.getOrderEnd() == time) {
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

        openTime = getWorkingTimeToday(getSchedule().getOrderBegin(), store.getTimeFormat(), 1000 * 60 * 60 * 8);
        closeTime = getWorkingTimeToday(getSchedule().getOrderEnd(), store.getTimeFormat(), 1000 * 60 * 60 * 23);

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

    public ITable getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(ITable selectedTable) {
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
