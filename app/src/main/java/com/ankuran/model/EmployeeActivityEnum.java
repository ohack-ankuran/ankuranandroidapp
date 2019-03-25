package com.ankuran.model;

public class EmployeeActivityEnum {
    public enum ActivityType {
        DUE, PAYMENT
    }

    public enum DueDistributionType {
        INDIVIDUAL, GROUP
    }

    public enum ActivityStatus {
        CORRECT, INCORRECT, DELETED
    }
}
