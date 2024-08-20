package com.motta.insurance_association_service.model;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@Data
public class SchemeDTO {

	private int id;
	private String name;
	private Date validFromDate;
	private Date validToDate;
	private double schemeAmount;
	private String schemeType;
	private double share;
	private double commission;
	private double brokerage;

	public SchemeDTO(int id, String name, Date validFromDate, Date validToDate,
					 double schemeAmount, String schemeType, double share,
					 double commission, double brokerage) {
		this.id = id;
		this.name = name;
		this.validFromDate = validFromDate;
		this.validToDate = validToDate;
		this.schemeAmount = schemeAmount;
		this.schemeType = schemeType;
		this.share = share;
		this.commission = commission;
		this.brokerage = brokerage;
	}

	public SchemeDTO() {

	}
}