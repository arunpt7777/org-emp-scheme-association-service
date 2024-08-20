package com.motta.insurance_association_service.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class AssociationDTO {

	private int id;
	private int employeeId;
	private int schemeId;

	public AssociationDTO() {
	}

	public AssociationDTO(int id, int employeeId, int schemeId) {
		this.id = id;
		this.employeeId = employeeId;
		this.schemeId = schemeId;
	}
}
