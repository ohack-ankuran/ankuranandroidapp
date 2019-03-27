package com.ankuran.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ItemFilterView implements Serializable {
    public Item item;
    public ItemCategory category;
    public List<ItemLabel> labels;

    public ItemFilterView(){

    }

    public ItemFilterView(Item item, ItemCategory category) {
        this.item = item;
        this.category = category;
    }

    public ItemFilterView(Item item, List<ItemLabel> labels) {
        this.item = item;
        this.labels = labels;
    }

    public ItemFilterView(Item item, ItemCategory category, List<ItemLabel> labels) {
        this.item = item;
        this.category = category;
        this.labels = labels;
    }
}
