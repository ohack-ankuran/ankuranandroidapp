package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Activity implements Serializable {
    public String id;
    public String timeCreated;
    public String type;
    public String status;
    public Boolean changeHistory;
    public DueDetail dueDetails;
    //public Object paymentDetails;
}
