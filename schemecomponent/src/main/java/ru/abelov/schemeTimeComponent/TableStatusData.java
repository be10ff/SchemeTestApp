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

import ru.abelov.schemeTimeComponent.entity.Break;
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
    private long duration;


    public TableStatusData(long currentDate, IStore store, IUser user, long orderStart, long interval, long delay, int politics, String timeFormat) {
        this.currentDate = currentDate;
        this.store = store;
        this.delay = delay;
        this.user = user;

        openTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderBegin(), store.getTimeFormat(), 1000 * 60 * 60 * 8));
        closeTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderEnd(), store.getTimeFormat(), 1000 * 60 * 60 * 23));
        if(closeTime <= openTime){
            closeTime += AlarmManager.INTERVAL_DAY;
        }
//        orderStart
        this.orderStart = orderStart;
//        if(currentDate + interval > closeTime){
//            this.currentDate += AlarmManager.INTERVAL_DAY;
//            openTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderBegin(), store.getTimeFormat(), 1000 * 60 * 60 * 8));
//            closeTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderEnd(), store.getTimeFormat(), 1000 * 60 * 60 * 23));
//
//            this.orderStart = openTime;
//        }
        while(this.orderStart + interval > closeTime){
            this.orderStart -= interval;
        }

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
        if(getBreaks() != null) {
            for (Break b : getBreaks()) {

                if (time >= b.breakBegin && time <b.breakEnd) {
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
        if(getBreaks() != null) {
            for (Break b : getBreaks()) {
                if (!(b.breakBegin >= orderStop || b.breakEnd <= orderStart)/*|| status.available == 1*/) {
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

    public List<Break> getBreaks(){
        List<Break> result = new ArrayList<>();
        if(getSchedule() != null && getSchedule().getBreaks() != null) {
            for (IBreak b : getSchedule().getBreaks()) {
                Break corr = new Break(getWorkingTimeToday(stringDate2Long(b.getBreakBegin(),"hhmm", 0)),
                        getWorkingTimeToday(stringDate2Long(b.getBreakEnd(),"hhmm", 0)));
                if(corr.breakBegin < openTime) {
                    corr.breakBegin += AlarmManager.INTERVAL_DAY;
                }

                if(corr.breakBegin > corr.breakEnd) {
                    corr.breakEnd += AlarmManager.INTERVAL_DAY;
                }

                result.add(corr);
            }
        }
        return result;
    }

    public IDaySchedule getSchedule(){
        Calendar current = (Calendar) Calendar.getInstance().clone();
        current.setTimeInMillis(currentDate);

            if (store.getSchedule() != null
                    && store.getSchedule().getSchedule() != null
                    && store.getSchedule().getSchedule().size() == 7) {

                switch (current.get(Calendar.DAY_OF_WEEK)){
                    case 1: //sunday
                        return store.getSchedule().getSchedule().get(6);
                    default: // 2 - MO, etc ... 7 - SAT
                        return store.getSchedule().getSchedule().get(current.get(Calendar.DAY_OF_WEEK) - 2);
                }

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


        public long stringDate2Long(String openTime, String timeFormat, long defaultTime){
            SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.getDefault());
            Calendar dateTime = (Calendar) Calendar.getInstance().clone();
            try {
                dateTime.setTime(format.parse(openTime));
            } catch (ParseException e) {
                dateTime.setTimeInMillis(defaultTime);
            }

            return dateTime.getTimeInMillis();
        }


    public long getWorkingTimeToday(long time) {
        Calendar dateTime = (Calendar) Calendar.getInstance().clone();
        dateTime.setTimeInMillis(time);

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

        if(getBreaks() != null){
            for(Break b : getBreaks()){

                if (time == b.breakBegin) {
                    res = res |0b01;
                }

                if (time == b.breakEnd) {
                    res = res |0b10;
                }

                if (time < b.breakEnd && time > b.breakBegin) {
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

        openTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderBegin(), store.getTimeFormat(), 1000 * 60 * 60 * 8));
        closeTime = getWorkingTimeToday(stringDate2Long(getSchedule().getOrderEnd(), store.getTimeFormat(), 1000 * 60 * 60 * 23));

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
