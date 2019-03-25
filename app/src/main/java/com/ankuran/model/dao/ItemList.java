package com.ankuran.model.dao;

import com.ankuran.model.Item;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ItemList implements Serializable {
    List<Item> items;
}
