package com.ankuran.model;

import com.ankuran.model.dao.DistributionEmployee;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DueDetail implements Serializable {
    public String id;
    public String distributionType;
    public Item item;
    public Integer quantity;
    public Double duePerItem;
    public Double amount;
    public List<DistributionEmployee> distribution;
}
