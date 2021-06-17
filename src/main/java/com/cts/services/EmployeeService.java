package com.cts.services;

import java.util.List;

import com.cts.entities.Department;
import com.cts.entities.Employee;
import com.cts.entities.PhoneType;

public interface EmployeeService {
	Employee createEmployee(Employee employee);

	List<Employee> findAllEmployees();
	
	List<Department> findDepartmentOfEmployees();
	
	List<Employee> findEmployeeByDepartmentAndState(String deptName, String... state);
	
	List<String> findEmployeePhoneNumbers(String dept, PhoneType type);
	
	List<Employee> findEmployeeWithTheirPhones();
	
	void generateReport();
	
	void raiseEmployeeSalary(Long id, Long inc);
	
	void removeEmployee(Department dept);

}
