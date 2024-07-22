package com.motta.insurance_association_service.service;

import java.util.List;

import com.motta.insurance_association_service.model.AssociationDTO;

public interface AssociationService {

	AssociationDTO createAssociation(AssociationDTO associationDTO);

	AssociationDTO retrieveAssociationById(Integer id);

	List<AssociationDTO> retrieveAllAssociations();

	AssociationDTO updateAssociation(AssociationDTO associationDTO);

	void deleteAssociation(Integer id);

}
