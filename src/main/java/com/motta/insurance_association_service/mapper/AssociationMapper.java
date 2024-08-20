package com.motta.insurance_association_service.mapper;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.model.AssociationDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AssociationMapper {

	// Convert Association JPA Entity into AssociationDTO
	public AssociationDTO mapToAssociationDTO(Association association) {
		AssociationDTO schemeDTO = new AssociationDTO();
		BeanUtils.copyProperties(association, schemeDTO);
        return schemeDTO;
	}

	// Convert AssociationDTO into Association JPA Entity
	public Association mapToAssociation(AssociationDTO associationDTO) {
		Association association = new Association();
		BeanUtils.copyProperties(association, associationDTO);
		return association;
	}
}
