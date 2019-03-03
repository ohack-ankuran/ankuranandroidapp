package com.ankuran.model.dao;

import com.ankuran.model.Employee;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeList implements Serializable {
    private List<Employee>employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
