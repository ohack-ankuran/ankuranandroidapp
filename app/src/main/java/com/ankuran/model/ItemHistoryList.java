package com.ankuran.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ItemHistoryList implements Serializable {
    List<ItemHistory>itemUpdateHistory;
}
