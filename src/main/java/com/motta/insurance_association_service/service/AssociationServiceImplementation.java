package com.motta.insurance_association_service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.insurance_association_service.exception.InvalidAssociationException;
import com.motta.insurance_association_service.exception.SchemeNotFoundException;
import com.motta.insurance_association_service.model.SchemeDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.motta.insurance_association_service.entity.Association;
import com.motta.insurance_association_service.exception.AssociationAlreadyExistsException;
import com.motta.insurance_association_service.mapper.AssociationMapper;
import com.motta.insurance_association_service.model.AssociationDTO;
import com.motta.insurance_association_service.repository.AssociationRepository;

import jakarta.transaction.Transactional;
import org.springframework.web.client.RestTemplate;

import static com.motta.insurance_association_service.util.AssociationConstants.*;

@Service
@Transactional
@Slf4j
public class AssociationServiceImplementation implements AssociationService {

	//private static final Logger log = LoggerFactory.getLogger(AssociationServiceImplementation.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AssociationMapper associationMapper;

	@Value("${association.id.initialValue}")
	private Integer initialValueOfPrimaryKey;

	@Autowired
	private AssociationRepository associationRepository;

	@Override
	public AssociationDTO createAssociation(AssociationDTO associationDTO) {

		//Validate the DTO before attempting to persist
		validateAssociationDTO(associationDTO);

		// Check if id already exists
		Optional<Association> association = associationRepository.findById(associationDTO.getId());
        log.info(LOG_MESSAGE_ASSOCIATION_NOT_FOUND, String.valueOf(associationDTO.getId()));

		if (association.isPresent()) {
			throw new AssociationAlreadyExistsException(LOG_MESSAGE_ASSOCIATION_NOT_FOUND + associationDTO.getId());
		}

		// Convert AssociationDTO into User JPA Entity
		Association newAssociation = associationMapper.mapToAssociation(associationDTO);
		Association savedAssociation = associationRepository.save(newAssociation);
		log.info(LOG_MESSAGE_ASSOCIATION_PERSISTED, associationDTO.getId());

		// Convert Association JPA entity to AssociationDTO
        return associationMapper.mapToAssociationDTO(savedAssociation);
	}

	// Get Association by id
	@Override
	public AssociationDTO retrieveAssociationById(Integer id) {
		Association association = associationRepository.findById(id).get();
		log.info(LOG_MESSAGE_FETCHED_ASSOCIATION, association.getClass());
        return associationMapper.mapToAssociationDTO(association);
	}

	// Get all Associations
	@Override
	public List<AssociationDTO> retrieveAllAssociations() {
		List<Association> associations = associationRepository.findAll();
		log.info(LOG_MESSAGE_FETCHED_ALL_ASSOCIATIONS);
		return associations.stream().map(associationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	@Override
	public AssociationDTO updateAssociation(AssociationDTO associationDTO) {

		//Validate the DTO before attempting to persist
		validateAssociationDTO(associationDTO);

		Association existingAssociation = associationRepository.findById(associationDTO.getId()).orElse(new Association(associationDTO.getId(), associationDTO.getEmployeeId(), associationDTO.getSchemeId()));
		existingAssociation.setEmployeeId(associationDTO.getEmployeeId());
		existingAssociation.setSchemeId(associationDTO.getSchemeId());


		Association updatedAssociation = associationRepository.save(existingAssociation);
		log.info(LOG_MESSAGE_ASSOCIATION_UPDATED, existingAssociation.getId());
		return associationMapper.mapToAssociationDTO(updatedAssociation);
	}

	@Override
	public void deleteAssociation(Integer id) {
		associationRepository.deleteById(id);
		log.error(LOG_MESSAGE_ASSOCIATION_DELETE_FAILED, id);

	}

	@Override
	public List<AssociationDTO> retrieveAssociationByEmployeeId(Integer employeeId) {
	List<Association> associations = associationRepository.findAll();
	return associations.stream().filter(asc -> asc.getEmployeeId()==employeeId)
			.map(associationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	@Override
	public Double calculateTotalAmountForEmployee(Integer employeeId) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		Double totalAmountOfEligibleSchemes = 0.0;
		Double totalOfFutureSchemes = 0.0;

		List <SchemeDTO> schemeDTOS = new ArrayList<>();

		// Get all associations for the given employeeId
		List<AssociationDTO> associationDTOS = retrieveAssociationByEmployeeId(employeeId);

		for (AssociationDTO associationDTO: associationDTOS) {
			// Call scheme-service
			ResponseEntity<SchemeDTO> response = restTemplate.exchange(URL_GET_SCHEME_BY_SCHEME_ID + associationDTO.getSchemeId(), HttpMethod.GET, entity, SchemeDTO.class);
			SchemeDTO schemeDTO = response.getBody();
			if (schemeDTO == null) {
				log.error(LOG_MESSAGE_ASSOCIATION_NOT_FOUND, associationDTO.getSchemeId());
				throw new SchemeNotFoundException("Scheme not found");
			}

			schemeDTOS.add(schemeDTO);
		}

		// Calculate total amount for future schemes
		List <SchemeDTO> futureSchemes = schemeDTOS.stream().filter(schemeDTO -> schemeDTO.getValidFromDate().after(new Date())).toList();
		for(SchemeDTO schemeDTO: futureSchemes) {
			totalOfFutureSchemes = totalOfFutureSchemes + schemeDTO.getSchemeAmount();
		}

		// Remove invalid schemes whose FromDate is after today or ToDate is before today
		schemeDTOS.removeIf(schemeDTO -> schemeDTO.getValidFromDate().after(new Date()) || schemeDTO.getValidToDate().before(new Date()));
		if (schemeDTOS.isEmpty()) return totalAmountOfEligibleSchemes;

		//Calculate total amount
		for(SchemeDTO schemeDTO: schemeDTOS) {
			totalAmountOfEligibleSchemes = totalAmountOfEligibleSchemes + schemeDTO.getSchemeAmount();
		}

		if(totalAmountOfEligibleSchemes> totalOfFutureSchemes)
		return totalAmountOfEligibleSchemes;
		else return  totalOfFutureSchemes;
	}
	@Override
	public void validateAssociationDTO(AssociationDTO associationDTO) {
		if (associationDTO.getId() == 0) {
			throw new InvalidAssociationException(EXCEPTION_MESSAGE_ASSOCIATION_ID_IS_MANDATORY);
		}

		if (associationDTO.getId()< initialValueOfPrimaryKey) {
			throw new InvalidAssociationException(EXCEPTION_MESSAGE_ASSOCIATION_ID_LESS_THAN_INITIAL_VALUE + initialValueOfPrimaryKey);
		}

		if (associationDTO.getSchemeId() == 0) {
			throw new InvalidAssociationException(EXCEPTION_MESSAGE_ASSOCIATION_ID_IS_MANDATORY);
		}
		if (associationDTO.getEmployeeId()==0) {
			throw new InvalidAssociationException(EXCEPTION_MESSAGE_EMPLOYEE_ID_IS_MANDATORY);
		}

	}

	@Override
	public List<AssociationDTO> retrieveAssociationsBySchemeId(Integer schemeId) {
		List<Association> associations = associationRepository.findAll();
		return associations.stream().filter(association -> association.getSchemeId() == schemeId )
				.map(associationMapper::mapToAssociationDTO).collect(Collectors.toList());
	}

	}
