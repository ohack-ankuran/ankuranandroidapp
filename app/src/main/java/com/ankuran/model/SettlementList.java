package com.ankuran.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SettlementList implements Serializable {
    List<Settlement>settlements;
    Double outstandingSettlement;
}
