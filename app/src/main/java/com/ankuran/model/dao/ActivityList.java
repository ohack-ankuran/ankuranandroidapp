package com.ankuran.model.dao;

import com.ankuran.model.Activity;
import com.ankuran.model.ActivityDetails;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ActivityList implements Serializable {
    List<Activity>activities;
}
