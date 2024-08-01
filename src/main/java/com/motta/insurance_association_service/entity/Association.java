package com.motta.insurance_association_service.entity;

import javax.validation.constraints.NotNull;

import jakarta.persistence.*;


import java.util.Objects;

@Entity
@Table(name = "employee_scheme_association")
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 100, allocationSize = 1)
public class Association {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private Integer id;
	private Integer employeeId;
	private Integer schemeId;

	public Association() {
	}

	public Association(Integer id, Integer employeeId, Integer schemeId) {
		this.id = id;
		this.employeeId = employeeId;
		this.schemeId = schemeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public @NotNull(message = "EmployeeId must not be empty") Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(@NotNull(message = "EmployeeId must not be empty") Integer employeeId) {
		this.employeeId = employeeId;
	}

	public @NotNull(message = "Scheme Id must not be empty") Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(@NotNull(message = "Scheme Id must not be empty") Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Override
	public String toString() {
		return "Association{" +
				"id=" + id +
				", employeeId=" + employeeId +
				", schemeId=" + schemeId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Association that = (Association) o;
		return Objects.equals(id, that.id) && Objects.equals(employeeId, that.employeeId) && Objects.equals(schemeId, that.schemeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, employeeId, schemeId);
	}
}
