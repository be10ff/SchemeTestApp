package ru.abelov.schemecomponent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by artem on 05.07.17.
 */

public class TableStatusesEntity implements Parcelable {
    public static final Creator<TableStatusesEntity> CREATOR = new Creator<TableStatusesEntity>() {
        @Override
        public TableStatusesEntity createFromParcel(Parcel in) {
            return new TableStatusesEntity(in);
        }

        @Override
        public TableStatusesEntity[] newArray(int size) {
            return new TableStatusesEntity[size];
        }
    };
    @SerializedName("id")
    public int id;
    @SerializedName("tableId")
    public int tableId;

//    @SerializedName("available")
//    public int available;
//
//    @SerializedName("busy")
//    public int busy;
//
//    @SerializedName("ordered")
//    public int ordered;
    @SerializedName("userId")
    public int userId;
    @SerializedName("orderBegin")
    public long orderBegin;
    @SerializedName("orderEnd")
    public long orderEnd;

    protected TableStatusesEntity(Parcel in) {
        id = in.readInt();
        tableId = in.readInt();
        userId = in.readInt();
        orderBegin = in.readLong();
        orderEnd = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(tableId);
        dest.writeInt(userId);
        dest.writeLong(orderBegin);
        dest.writeLong(orderEnd);
    }
}
