package com.motta.insurance_association_service.model;

import java.util.Date;
import java.util.Objects;

public class AssociationDTO {

	private Integer id;
	private Integer employeeId;
	private Integer schemeId;

	public AssociationDTO() {
	}

	public AssociationDTO(Integer id, Integer employeeId, Integer schemeId) {
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

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Override
	public String toString() {
		return "AssociationDTO{" +
				"id=" + id +
				", employeeId=" + employeeId +
				", schemeId=" + schemeId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AssociationDTO that = (AssociationDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(employeeId, that.employeeId) && Objects.equals(schemeId, that.schemeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, employeeId, schemeId);
	}
}
