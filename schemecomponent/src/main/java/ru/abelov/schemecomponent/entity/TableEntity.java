package ru.abelov.schemecomponent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artem on 05.07.17.
 */

public class TableEntity implements Parcelable {
    public static final Creator<TableEntity> CREATOR = new Creator<TableEntity>() {
        @Override
        public TableEntity createFromParcel(Parcel in) {
            return new TableEntity(in);
        }

        @Override
        public TableEntity[] newArray(int size) {
            return new TableEntity[size];
        }
    };
    @SerializedName("id")
    public long id;
    @SerializedName("brandId")
    public long brandId;
    @SerializedName("storeId")
    public long storeId;
    @SerializedName("sectionId")
    public long sectionId;
    @SerializedName("ordinal")
    public int ordinal;
    @SerializedName("name")
    public String name;
    @SerializedName("isUsed")
    public boolean isUsed;
    @SerializedName("enabled")
    public boolean enabled;
    @SerializedName("orderEnabled")
    public boolean orderEnabled;
    @SerializedName("posNo")
    public String posNo;
    @SerializedName("orderId")
    public int orderId;
    @SerializedName("masterTableNo")
    public int masterTableNo;
    @SerializedName("adminId")
    public String adminId;
    @SerializedName("imageTable")
    public String img;
    @SerializedName("capacity")
    public int capacity;
    @SerializedName("mapX")
    public int mapX;
    @SerializedName("mapY")
    public int mapY;
    @SerializedName("tableStatuses")
    public List<TableStatusesEntity> tableStatuses;
    @SerializedName("deposit")
    public double deposit;

    protected TableEntity(Parcel in) {
        id = in.readLong();
        brandId = in.readLong();
        storeId = in.readLong();
        sectionId = in.readLong();
        ordinal = in.readInt();
        name = in.readString();
        isUsed = in.readByte() != 0;
        enabled = in.readByte() != 0;
        orderEnabled = in.readByte() != 0;
        orderId = in.readInt();
        masterTableNo = in.readInt();
        adminId = in.readString();
        img = in.readString();
        capacity = in.readInt();
        mapX = in.readInt();
        mapY = in.readInt();

        tableStatuses = in.createTypedArrayList(TableStatusesEntity.CREATOR);
        deposit = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(brandId);
        dest.writeLong(storeId);
        dest.writeLong(sectionId);
        dest.writeInt(ordinal);
        dest.writeString(name);
        dest.writeByte((byte) (isUsed ? 1 : 0));
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeByte((byte) (orderEnabled ? 1 : 0));
        dest.writeInt(orderId);
        dest.writeInt(masterTableNo);
        dest.writeString(adminId);
        dest.writeString(img);
        dest.writeInt(capacity);
        dest.writeInt(mapX);
        dest.writeInt(mapY);
        dest.writeTypedList(tableStatuses);
        dest.writeDouble(deposit);

    }
}
