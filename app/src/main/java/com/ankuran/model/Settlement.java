package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Settlement implements Serializable {
    long id;
    Integer centreId;
    String timeCreated;
    double amountBefore;
    double amount;
    double amountAfter;
    Employee settledBy;
    Employee counterparty;
    boolean correction;
    String notes;
}
