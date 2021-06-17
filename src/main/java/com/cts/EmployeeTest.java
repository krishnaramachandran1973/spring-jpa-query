package com.cts;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cts.config.JpaConfig;
import com.cts.entities.Address;
import com.cts.entities.Department;
import com.cts.entities.Employee;
import com.cts.entities.Phone;
import com.cts.entities.PhoneType;
import com.cts.services.DepartmentService;
import com.cts.services.EmployeeService;

public class EmployeeTest {
	private static final Logger log = LoggerFactory.getLogger(EmployeeTest.class);

	public static void main(String[] args) {
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("app-context.xml");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfig.class);
		EmployeeService empService = ctx.getBean("employeeService", EmployeeService.class);
		DepartmentService deptService = ctx.getBean("departmentService", DepartmentService.class);

		log.info(empService.getClass().getCanonicalName());// Proxy&5ljgljg

		/*
		 * Department it = Department.builder().name("IT").build(); Department
		 * hr = Department.builder().name("HR").build(); Department mgmt =
		 * Department.builder().name("Management").build();
		 */

		/*
		 * deptService.createDepartment(it); deptService.createDepartment(mgmt);
		 * deptService.createDepartment(hr);
		 * 
		 * empService.createEmployee(Employee.builder().name("Prachi").salary(
		 * 10000L).comments("PL")
		 * .address(Address.builder().city("Pune").state("Maharashtra").build())
		 * .department(it)
		 * .phones(Arrays.asList(Phone.builder().number("813547874").type(
		 * PhoneType.MOBILE).build())).build());
		 * 
		 * empService.createEmployee(Employee.builder().name("Rohit").salary(
		 * 10000L).comments("Software Enginner")
		 * .address(Address.builder().city("Pune").state("Maharashtra").build())
		 * .department(it)
		 * .phones(Arrays.asList(Phone.builder().number("745154785").type(
		 * PhoneType.MOBILE).build())).build());
		 * 
		 * empService.createEmployee(Employee.builder().name("Vijay").salary(
		 * 10000L).comments("TL")
		 * .address(Address.builder().city("Chennai").state("TN").build()).
		 * department(it)
		 * .phones(Arrays.asList(Phone.builder().number("471-2458758").type(
		 * PhoneType.LANDLINE).build())).build());
		 * 
		 * empService.createEmployee(Employee.builder().name("Sravya").salary(
		 * 30000L).comments("Director")
		 * .address(Address.builder().city("Jaipur").state("Rajasthan").build())
		 * .department(mgmt)
		 * .phones(Arrays.asList(Phone.builder().number("56434546").type(
		 * PhoneType.MOBILE).build())).build());
		 */

		// empService.findAllEmployees().forEach(emp -> log.info("{}", emp));

		/*
		 * empService.findDepartmentOfEmployees().forEach(dept -> log.info("{}",
		 * dept));
		 * 
		 * empService.findEmployeeByDepartmentAndState("IT", new String[] {
		 * "TN", "Maharashtra" }) .forEach(dep -> log.info("{}", dep));
		 */

		// empService.findEmployeePhoneNumbers("IT",
		// PhoneType.MOBILE).forEach(ph -> log.info(ph));

		/*
		 * empService.findEmployeeWithTheirPhones().forEach(emp -> {
		 * log.info("{}", emp); emp.getPhones().forEach(ph -> log.info("{}",
		 * ph));
		 * 
		 * });
		 */

		empService.raiseEmployeeSalary(4L, 7428L);
		empService.removeEmployee(Department.builder().id(2L).build());

		empService.generateReport();

	}
}
