package com.cts.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByName(String name);

	@Query("SELECT e FROM Employee e WHERE e.comments LIKE %?1%")
	List<Employee> findByCustomQuery(String comments);

}
