package com.cts.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NamedQueries({ @NamedQuery(name = "Employee.FIND_ALL_DEPARTMENTS", query = "SELECT e.department FROM Employee e"),
		@NamedQuery(name = "Employee.FETCH_PHONES_WITH_EMPLOYEES", query = "SELECT e FROM Employee e JOIN FETCH e.phones"),
		@NamedQuery(name = "Employee.UPDATE_SALRY", query = "UPDATE Employee e SET e.salary=e.salary + :sal WHERE e.id=:id"),
		@NamedQuery(name = "Employee.DELETE_EMPLOYEE_FROM_DEPT", query = "DELETE FROM Employee e WHERE e.department=:dept") })

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	@Column(name = "SAL")
	private Long salary;

	@NonNull
	@Basic(fetch = FetchType.LAZY)
	private String comments;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPT_ID")
	private Department department;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "ADDR_ID", foreignKey = @ForeignKey(name = "FK_ADDR"))
	private Address address;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Default
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(name = "EMP_PHONE", joinColumns = @JoinColumn(name = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
	private List<Phone> phones = new ArrayList<>();
}
