package com.ankuran.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeHusband implements Serializable, Parcelable {
    private String bslEmployeeId;
    private String fullName;

    public EmployeeHusband() {

    }

    protected EmployeeHusband(Parcel in) {
        bslEmployeeId = in.readString();
        fullName = in.readString();
    }

    public static final Creator<EmployeeHusband> CREATOR = new Creator<EmployeeHusband>() {
        @Override
        public EmployeeHusband createFromParcel(Parcel in) {
            return new EmployeeHusband(in);
        }

        @Override
        public EmployeeHusband[] newArray(int size) {
            return new EmployeeHusband[size];
        }
    };

    public String getBslEmployeeId() {
        return bslEmployeeId;
    }

    public void setBslEmployeeId(String bslEmployeeId) {
        this.bslEmployeeId = bslEmployeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bslEmployeeId);
        dest.writeString(fullName);
    }
}
