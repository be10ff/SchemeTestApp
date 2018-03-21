package ru.abelov.schemeTimeComponent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artem on 05.07.17.
 */

public class SectionEntity implements Parcelable {

    public static final Creator<SectionEntity> CREATOR = new Creator<SectionEntity>() {
        @Override
        public SectionEntity createFromParcel(Parcel in) {
            return new SectionEntity(in);
        }

        @Override
        public SectionEntity[] newArray(int size) {
            return new SectionEntity[size];
        }
    };
    @SerializedName("id")
    public long id;
    @SerializedName("brandId")
    public long brandId;
    @SerializedName("storeId")
    public long storeId;
    @SerializedName("name")
    public String name;
    @SerializedName("schemaImg")
    public String schemaImg;
    @SerializedName("adminId")
    public String adminId;
    @SerializedName("ordinal")
    public int ordinal;
    @SerializedName("tables")
    public List<TableEntity> tables;

    public SectionEntity(Parcel src) {
        this.id = src.readLong();
        this.brandId = src.readLong();
        this.storeId = src.readLong();
        this.name = src.readString();
        this.schemaImg = src.readString();
        this.adminId = src.readString();
        this.ordinal = src.readInt();
        tables = src.createTypedArrayList(TableEntity.CREATOR);
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
        dest.writeString(name);
        dest.writeString(schemaImg);
        dest.writeString(adminId);
        dest.writeLong(ordinal);
        dest.writeTypedList(tables);
    }
}
