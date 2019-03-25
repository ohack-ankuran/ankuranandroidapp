package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaymentDetails implements Serializable {
    private Double currentDue;
    private Double remainingDue;
    private String id;
    private String note;
    private Double amount;
}
