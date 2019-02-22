package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DueDetail implements Serializable {
    public String id;
    public String distributionType;
    public Item item;
    public Integer quantity;
    public Integer duePerItem;
    public Integer amount;
}
