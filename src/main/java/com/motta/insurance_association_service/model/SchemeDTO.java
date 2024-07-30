package com.motta.insurance_association_service.model;

import java.util.Date;
import java.util.Objects;

public class SchemeDTO {

	private Integer id;
	private String name;
	private Date validFromDate;
	private Date validToDate;
	private Double schemeAmount;
	private String schemeType;
	private Double share;
	private Double brokerage;

	public SchemeDTO() {
	}

	public SchemeDTO(Integer id, String name, Date validFromDate, Date validToDate, Double schemeAmount, String schemeType, Double share, Double brokerage) {
		this.id = id;
		this.name = name;
		this.validFromDate = validFromDate;
		this.validToDate = validToDate;
		this.schemeAmount = schemeAmount;
		this.schemeType = schemeType;
		this.share = share;
		this.brokerage = brokerage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	public Double getSchemeAmount() {
		return schemeAmount;
	}

	public void setSchemeAmount(Double schemeAmount) {
		this.schemeAmount = schemeAmount;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public Double getShare() {
		return share;
	}

	public void setShare(Double share) {
		this.share = share;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	@Override
	public String toString() {
		return "SchemeDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", validFromDate=" + validFromDate +
				", validToDate=" + validToDate +
				", schemeAmount=" + schemeAmount +
				", schemeType='" + schemeType + '\'' +
				", share=" + share +
				", brokerage=" + brokerage +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SchemeDTO schemeDTO = (SchemeDTO) o;
		return Objects.equals(id, schemeDTO.id) && Objects.equals(name, schemeDTO.name) && Objects.equals(validFromDate, schemeDTO.validFromDate) && Objects.equals(validToDate, schemeDTO.validToDate) && Objects.equals(schemeAmount, schemeDTO.schemeAmount) && Objects.equals(schemeType, schemeDTO.schemeType) && Objects.equals(share, schemeDTO.share) && Objects.equals(brokerage, schemeDTO.brokerage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, validFromDate, validToDate, schemeAmount, schemeType, share, brokerage);
	}
}
