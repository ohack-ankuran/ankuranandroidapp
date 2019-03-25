package com.ankuran.model;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}
