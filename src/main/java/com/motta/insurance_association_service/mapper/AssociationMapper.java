package com.motta.insurance_association_service.mapper;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.model.AssociationDTO;

public class AssociationMapper {

	// Convert Association JPA Entity into AssociationDTO
	public static AssociationDTO mapToAssociationDTO(Association association) {
        return new AssociationDTO(association.getId(), association.getEmployeeId(), association.getSchemeId());
	}

	// Convert AssociationDTO into Association JPA Entity
	public static Association mapToAssociation(AssociationDTO associationDTO) {
        return new Association(associationDTO.getId(), associationDTO.getEmployeeId(), associationDTO.getSchemeId());
	}
}
