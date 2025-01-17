package com.motta.insurance_association_service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.insurance_association_service.exception.AssociationNotFoundException;
import com.motta.insurance_association_service.exception.InvalidAssociationException;
import com.motta.insurance_association_service.exception.SchemeNotFoundException;
import com.motta.insurance_association_service.model.SchemeDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.exception.AssociationAlreadyExistsException;
import com.motta.insurance_association_service.mapper.AssociationMapper;
import com.motta.insurance_association_service.model.AssociationDTO;
import com.motta.insurance_association_service.repository.AssociationRepository;

import jakarta.transaction.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class AssociationServiceImplementation implements AssociationService {

	private static final Logger log = LoggerFactory.getLogger(AssociationServiceImplementation.class);

	@Value("${association.id.initalValue}")
	private Integer initalValueOfPrimaryKey;

	@Autowired
	private AssociationRepository repository;

	@Override
	public AssociationDTO createAssociation(AssociationDTO associationDTO) {

		//Validate the DTO before attempting to persist
		validateAssociationDTO(associationDTO);

		// Check if id already exists
		Optional<Association> association = repository.findById(associationDTO.getId());
		log.error("Association id = {} not found. Please enter different id", associationDTO.getId());

		if (association.isPresent()) {
			throw new AssociationAlreadyExistsException("Association id = " + associationDTO.getId() + " already Exists!");
		}

		// Convert AssociationDTO into User JPA Entity
		Association newAssociation = AssociationMapper.mapToAssociation(associationDTO);
		Association savedAssociation = repository.save(newAssociation);
		log.info("Association id = {} has been persisted.", associationDTO.getId());

		// Convert Association JPA entity to AssociationDTO
        return AssociationMapper.mapToAssociationDTO(savedAssociation);
	}

	// Get Association by id
	@Override
	public AssociationDTO retrieveAssociationById(Integer id) {
		Association association = repository.findById(id).get();
		log.info("Association id = {} has been fetched.", association.getId());
        return AssociationMapper.mapToAssociationDTO(association);
	}

	// Get all Associations
	@Override
	public List<AssociationDTO> retrieveAllAssociations() {
		List<Association> associations = repository.findAll();
		log.info("All associations have been fetched using .");
		return associations.stream().map(AssociationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	@Override
	public AssociationDTO updateAssociation(AssociationDTO associationDTO) {

		//Validate the DTO before attempting to persist
		validateAssociationDTO(associationDTO);

		Association existingAssociation = repository.findById(associationDTO.getId()).orElse(new Association(associationDTO.getId(), associationDTO.getEmployeeId(), associationDTO.getSchemeId()));
		existingAssociation.setEmployeeId(associationDTO.getEmployeeId());
		existingAssociation.setSchemeId(associationDTO.getSchemeId());


		Association updatedAssociation = repository.save(existingAssociation);
		log.error("Updating association id = {} has failed.", existingAssociation.getId());
		return AssociationMapper.mapToAssociationDTO(updatedAssociation);
	}

	@Override
	public void deleteAssociation(Integer id) {
		repository.deleteById(id);
		log.error("Deleting association id = {} has failed .", id);

	}

	@Override
	public List<AssociationDTO> retrieveAssociationByEmployeeId(Integer employeeId) {
	List<Association> associations = repository.findAll();
	return associations.stream().filter(asc -> asc.getEmployeeId().equals(employeeId))
			.map(AssociationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	@Override
	public Double calculateTotalAmountForEmployee(Integer employeeId) {
		Double totalAmount = 0.0;
		List <SchemeDTO> schemeDTOS = new ArrayList<>();

		// Get all associations for the given employeeId
		List<AssociationDTO> associationDTOS = retrieveAssociationByEmployeeId(employeeId);

		for (AssociationDTO associationDTO: associationDTOS) {
			// Call scheme-service
			ResponseEntity<SchemeDTO> schemeResponseEntity = new RestTemplate()
					.getForEntity("http://localhost:8800/schemes/{schemeId}", SchemeDTO.class, associationDTO.getSchemeId());
			SchemeDTO schemeDTO = schemeResponseEntity.getBody();
			if (schemeDTO == null) {
				log.error("Fetching scheme id = {} has failed.", associationDTO.getSchemeId());
				throw new SchemeNotFoundException("Scheme not found");
			}
			schemeDTOS.add(schemeDTO);
		}

		// Remove invalid schemes whose FromDate is after today or ToDate is before today
		schemeDTOS.removeIf(schemeDTO -> schemeDTO.getValidFromDate().after(new Date()) || schemeDTO.getValidToDate().before(new Date()));
		if (schemeDTOS.isEmpty()) return 0.0;

		//Calculate total amount
		for(SchemeDTO schemeDTO: schemeDTOS) {
			totalAmount = totalAmount + schemeDTO.getSchemeAmount();
		}
		return totalAmount;
	}
	@Override
	public void validateAssociationDTO(AssociationDTO associationDTO) {
		if (associationDTO.getId()==null) {
			throw new InvalidAssociationException("Association Id is mandatory");
		}

		if (associationDTO.getId()<initalValueOfPrimaryKey) {
			throw new InvalidAssociationException("Association Id must not be less than the initial value of: " + initalValueOfPrimaryKey);
		}

		if (associationDTO.getSchemeId()==null) {
			throw new InvalidAssociationException("Scheme Id is mandatory");
		}
		if (associationDTO.getEmployeeId()==null) {
			throw new InvalidAssociationException("Employee Id is mandatory");
		}

	}


	}
