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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTimeOfJoining() {
        return timeOfJoining;
    }

    public void setTimeOfJoining(String timeOfJoining) {
        this.timeOfJoining = timeOfJoining;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public EmployeeHusband getHusband() {
        return husband;
    }

    public void setHusband(EmployeeHusband husband) {
        this.husband = husband;
    }

    public Integer getCentre() {
        return centre;
    }

    public void setCentre(Integer centre) {
        this.centre = centre;
    }

    public float getOutstandingDue() {
        return outstandingDue;
    }

    public void setOutstandingDue(float outstandingDue) {
        this.outstandingDue = outstandingDue;
    }
}
