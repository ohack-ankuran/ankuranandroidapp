package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemHistory implements Serializable {
    private Long id;
    private String  timeCreated;
    private Long centreId;
    private Long itemId;
    private Long activityId;
    private ItemHistoryEnum.HistoryType type;
    private ItemHistoryEnum.HistoryReason reason;
    private String actorFullName;
    private Long units;
    private Double actualUnitSalePrice;
    private Double totalAmount;
    private String notes;
    private Boolean deleted;

}
