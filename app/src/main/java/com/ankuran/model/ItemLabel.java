package com.ankuran.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemLabel implements Serializable {
    public String label;
    public boolean isSelected;

    public ItemLabel() {

    }

    public ItemLabel(String label, boolean isSelected) {
        this.label = label;
        this.isSelected = isSelected;
    }
}
