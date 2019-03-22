package com.ankuran.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class GroupWageEmployee implements Serializable, Parcelable {
    private Employee mEmployee;
    private Boolean mIsSelected;
    private Double mWage;

    public GroupWageEmployee(Employee mEmployee, Boolean mIsSelected, Double mWage) {
        this.mEmployee = mEmployee;
        this.mIsSelected = mIsSelected;
        this.mWage = mWage;
    }

    public GroupWageEmployee(Parcel in) {
        mEmployee = (Employee) in.readValue(Employee.class.getClassLoader());
        mIsSelected = (Boolean) in.readValue(null);
        mWage = (Double) in.readDouble();
    }

    public static final Creator<GroupWageEmployee> CREATOR = new Creator<GroupWageEmployee>() {
        @Override
        public GroupWageEmployee createFromParcel(Parcel in) {
            return new GroupWageEmployee(in);
        }

        @Override
        public GroupWageEmployee[] newArray(int size) {
            return new GroupWageEmployee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mEmployee);
        dest.writeValue(mIsSelected);
        dest.writeDouble(mWage);
    }

    public Employee getEmployee() {
        return mEmployee;
    }

    public void setEmployee(Employee employee) {
        mEmployee = employee;
    }

    public Boolean getSelected() {
        return mIsSelected;
    }

    public void setSelected(Boolean selected) {
        mIsSelected = selected;
    }

    public Double getWage() {
        return mWage;
    }

    public void setWage(Double wage) {
        mWage = wage;
    }
}
