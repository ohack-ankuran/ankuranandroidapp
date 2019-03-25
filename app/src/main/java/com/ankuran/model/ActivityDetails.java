package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityDetails implements Serializable {
    private Long id;
    private String timeCreated;
    private EmployeeActivityEnum.ActivityType type;
    private EmployeeActivityEnum.ActivityStatus status;
    private boolean changeHistory;
    private DueDetail dueDetails;
    private PaymentDetails paymentDetails;
}
