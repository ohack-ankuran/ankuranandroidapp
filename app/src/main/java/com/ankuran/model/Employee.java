package com.ankuran.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ankuran.util.AppUtils;

import java.io.Serializable;

import lombok.Data;

@Data
public class Employee implements Serializable {
    public long id;
    public String fullName;
    public String mobile;
    public String timeOfJoining;
    public Boolean active;
    public EmployeeHusband husband;
    public Integer centre;
    public float outstandingDue;

    public Employee() {
        //TODO check timeOfJoining format and set it there.
        this.timeOfJoining = AppUtils.getCurrentDate();
    }

    protected Employee(Parcel in) {
        id = in.readLong();
        fullName = in.readString();
        mobile = in.readString();
        timeOfJoining = in.readString();
        byte tmpActive = in.readByte();
        active = tmpActive == 0 ? null : tmpActive == 1;
        husband = in.readParcelable(EmployeeHusband.class.getClassLoader());
        if (in.readByte() == 0) {
            centre = null;
        } else {
            centre = in.readInt();
        }
        outstandingDue = in.readFloat();
    }

//    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
//        @Override
//        public Employee createFromParcel(Parcel in) {
//            return new Employee(in);
//        }
//
//        @Override
//        public Employee[] newArray(int size) {
//            return new Employee[size];
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
//        dest.writeLong(id);
//        dest.writeString(fullName);
//        dest.writeString(mobile);
//        dest.writeString(timeOfJoining);
//        dest.writeByte((byte) (active == null ? 0 : active ? 1 : 2));
//        dest.writeParcelable(husband, flags);
//        if (centre == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeInt(centre);
//        }
//        dest.writeFloat(outstandingDue);
//    }
}
