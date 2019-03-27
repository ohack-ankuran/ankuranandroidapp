package com.ankuran.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ankuran.util.AppUtils;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Item implements Serializable {
    public Integer id;
    public String name;
    public String category;
    public String picture;
    public List<String> labels;
    public String timeCreated;
    public Employee addedBy;
    public Boolean active;
    public int availableUnits;

    public Item() {
        this.timeCreated = AppUtils.getCurrentDate();
    }


    protected Item(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        category = in.readString();
        picture = in.readString();
        labels = in.createStringArrayList();
        timeCreated = in.readString();
        byte tmpActive = in.readByte();
        active = tmpActive == 0 ? null : tmpActive == 1;
        availableUnits = in.readInt();
    }

//    public static final Creator<Item> CREATOR = new Creator<Item>() {
//        @Override
//        public Item createFromParcel(Parcel in) {
//            return new Item(in);
//        }
//
//        @Override
//        public Item[] newArray(int size) {
//            return new Item[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeString(category);
//        dest.writeString(picture);
//        dest.writeList(labels);
//        dest.writeString(timeCreated);
//        dest.writeValue(addedBy);
//        dest.writeValue(active);
//        dest.writeInt(availableUnits);
//    }
}
