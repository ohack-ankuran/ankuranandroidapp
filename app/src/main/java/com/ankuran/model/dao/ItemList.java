package com.ankuran.model.dao;

import com.ankuran.model.Item;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ItemList implements Serializable {
    List<Item> products;

    public List<Item> getProducts() {
        return products;
    }

    public void setProducts(List<Item> products) {
        this.products = products;
    }
}
