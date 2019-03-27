package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemCategory implements Serializable {
    public String category;
    public boolean isSelected;

    public ItemCategory() {

    }

    public ItemCategory(String category, boolean isSelected) {
        this.category = category;
        this.isSelected = isSelected;
    }
}
