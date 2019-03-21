package com.ankuran.model.dao;

import com.ankuran.model.Employee;

import java.io.Serializable;

import lombok.Data;

@Data
public class DistributionEmployee implements Serializable {
    public Employee employee;
    public Double amount;
}
