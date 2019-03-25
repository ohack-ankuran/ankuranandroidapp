package com.ankuran.model.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.ankuran.model.Employee;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeList implements Serializable {
    private List<Employee> employees;

//    protected EmployeeList(Parcel in) {
//        employees = in.createTypedArrayList(Employee.CREATOR);
//    }
//
//    public static final Creator<EmployeeList> CREATOR = new Creator<EmployeeList>() {
//        @Override
//        public EmployeeList createFromParcel(Parcel in) {
//            return new EmployeeList(in);
//        }
//
//        @Override
//        public EmployeeList[] newArray(int size) {
//            return new EmployeeList[size];
//        }
//    };

//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeTypedList(employees);
//    }
}
