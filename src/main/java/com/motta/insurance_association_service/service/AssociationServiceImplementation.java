package com.motta.insurance_association_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.exception.AssociationAlreadyExistsException;
import com.motta.insurance_association_service.mapper.AssociationMapper;
import com.motta.insurance_association_service.model.AssociationDTO;
import com.motta.insurance_association_service.repository.AssociationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AssociationServiceImplementation implements AssociationService {

	@Autowired
	private AssociationRepository repository;

	@Override
	public AssociationDTO createAssociation(AssociationDTO associationDTO) {

		// CHeck if id already exists
		Optional<Association> association = repository.findById(associationDTO.getId());
		if (association.isPresent()) {
			throw new AssociationAlreadyExistsException("Association id = " + associationDTO.getId() + " already Exists!");
		}

		// Convert AssociationDTO into User JPA Entity
		Association newAssociation = AssociationMapper.mapToAssociation(associationDTO);
		Association savedAssociation = repository.save(newAssociation);

		// Convert Association JPA entity to AssociationDTO
        return AssociationMapper.mapToAssociationDTO(savedAssociation);
	}

	@Override
	public AssociationDTO retrieveAssociationById(Integer id) {
		Association association = repository.findById(id).get();
        return AssociationMapper.mapToAssociationDTO(association);
	}

	@Override
	public List<AssociationDTO> retrieveAllAssociations() {
		List<Association> associations = repository.findAll();
		return associations.stream().map(AssociationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	@Override
	public AssociationDTO updateAssociation(AssociationDTO associationDTO) {
		Association existingAssociation = repository.findById(associationDTO.getId()).orElse(new Association(associationDTO.getId(), associationDTO.getEmployeeId(), associationDTO.getSchemeId()));
			existingAssociation.setEmployeeId(associationDTO.getEmployeeId());
			existingAssociation.setSchemeId(associationDTO.getSchemeId());

		Association updatedAssociation = repository.save(existingAssociation);
		return AssociationMapper.mapToAssociationDTO(updatedAssociation);
	}

	@Override
	public void deleteAssociation(Integer id) {
		repository.deleteById(id);
	}

}
