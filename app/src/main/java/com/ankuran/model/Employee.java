package com.ankuran.model;

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
        this.timeOfJoining="2019-01-12T00:15:21.030Z";
    }
}
