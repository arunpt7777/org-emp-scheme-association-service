package com.motta.insurance_association_service.entity;

import javax.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Objects;

@Entity
@Table(name = "employee_scheme_association")
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 100, allocationSize = 1)
@Data
public class Association {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private int id;
	private int employeeId;
	private int schemeId;

	public Association() {
	}

	public Association(int id, int employeeId, int schemeId) {
		this.id = id;
		this.employeeId = employeeId;
		this.schemeId = schemeId;
	}
}
