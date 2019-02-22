package com.ankuran.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Item implements Serializable {
    public Integer id;
    public String name;
    public String type;
    public String picture;
    public List<String> labels;
    public String timeCreated;
    public Employee addedBy;
    public Boolean active;
}
