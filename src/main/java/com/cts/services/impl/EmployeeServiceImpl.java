package com.cts.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.entities.Address;
import com.cts.entities.Department;
import com.cts.entities.Employee;
import com.cts.entities.PhoneType;
import com.cts.services.EmployeeService;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	/*
	 * @Autowired EmployeeRepository repo;
	 */

	@Override
	public Employee createEmployee(Employee employee) {
		logger.info("Creating an employee");
		em.persist(employee);
		em.flush();
		return employee;
	}

	@Override
	public List<Employee> findAllEmployees() {
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
		return query.getResultList();
	}

	@Override
	public List<Department> findDepartmentOfEmployees() {
		return em.createNamedQuery("Employee.FIND_ALL_DEPARTMENTS", Department.class).getResultList();

	}

	@Override
	public List<Employee> findEmployeeByDepartmentAndState(String deptName, String... state) {
		String query = "SELECT e FROM Employee e WHERE e.department.name ='" + deptName + "' AND e.address.state IN ('"
				+ state[0] + "','" + state[1] + "')";

		String query1 = "SELECT e FROM Employee e WHERE e.department.name =:deptName AND e.address.state IN (:state1,:state2)";
		return em.createQuery(query1, Employee.class).setParameter("deptName", deptName)
				.setParameter("state1", state[0]).setParameter("state2", state[1]).getResultList();
	}

	@Override
	public List<String> findEmployeePhoneNumbers(String dept, PhoneType type) {
		String query = "SELECT p.number FROM Employee e JOIN e.phones p WHERE e.department.name = :dept AND p.type = :type";
		return em.createQuery(query, String.class).setParameter("dept", dept).setParameter("type", type)
				.getResultList();
	}

	@Override
	public List<Employee> findEmployeeWithTheirPhones() {
		return em.createNamedQuery("Employee.FETCH_PHONES_WITH_EMPLOYEES", Employee.class).getResultList();
	}

	@Override
	public void generateReport() {
		String query = "SELECT d, COUNT(e), MAX(e.salary), AVG(e.salary) FROM Department d JOIN d.employees e GROUP BY d HAVING COUNT(e) >= 1";
		List result = em.createQuery(query).getResultList();
		int counter = 0;
		for (Iterator i = result.iterator(); i.hasNext();) {
			Object[] val = (Object[]) i.next();
			logger.info(++counter + ":" + val[0] + " ," + val[1] + ", " + val[2] + " ," + val[3]);
		}
	}

	@Override
	public void raiseEmployeeSalary(Long id, Long inc) {
		em.createNamedQuery("Employee.UPDATE_SALRY").setParameter("sal", inc).setParameter("id", id).executeUpdate();

	}

	@Override
	public void removeEmployee(Department dept) {
		em.createNamedQuery("Employee.DELETE_EMPLOYEE_FROM_DEPT").setParameter("dept", dept).executeUpdate();

	}

}
