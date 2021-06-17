package com.cts.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.entities.Department;
import com.cts.services.DepartmentService;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@PersistenceContext
	EntityManager em;

	@Override
	public void createDepartment(Department department) {
		em.persist(department);
	}

}
