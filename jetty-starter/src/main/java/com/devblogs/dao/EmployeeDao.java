package com.devblogs.dao;

import com.devblogs.model.Employee;

public interface EmployeeDao {
	int saveEmployee(Employee emp);
	Employee findEmployeeByName(String name);
}