package com.motta.insurance_association_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.insurance_association_service.exception.AssociationNotFoundException;
import com.motta.insurance_association_service.exception.SchemeNotFoundException;
import com.motta.insurance_association_service.model.SchemeDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public AssociationDTO calculateMaxAmount(Integer schemeId) {

		// Call employee-service
		ResponseEntity<SchemeDTO> schemeResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:8800/schemes/{schemeId}", SchemeDTO.class, schemeId);
		SchemeDTO schemeDTO = schemeResponseEntity.getBody();
		if (schemeDTO == null) {
			throw new SchemeNotFoundException("Scheme not found");
		}

		Integer salaryId = employeeDTO.getSalaryId();

		// Call salary-service
		ResponseEntity<SalaryDTO> salaryResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/salaries/{salaryId}", SalaryDTO.class, salaryId);
		SalaryDTO salaryDTO = salaryResponseEntity.getBody();
		if (salaryDTO == null) {
			throw new SalaryNotFoundException("Salary not found");
		}

		// Call attendance-service
		ResponseEntity<AttendanceDTO> attendancResponseEntity = new RestTemplate().getForEntity(
				"http://localhost:9000/attendancesByEmployeeId/{employeeId}", AttendanceDTO.class, employeeId);
		AttendanceDTO attendanceDTO = attendancResponseEntity.getBody();

		if (attendanceDTO == null) {
			throw new AttendanceNotFoundException("Attendance not found");
		}

		// return salary object with updated salary
		Double totalSalary = salaryDTO.getSalaryPerDay() * attendanceDTO.getNumberOfWorkingDays();
		salaryDTO.setTotalSalary(totalSalary);
		return salaryDTO;
	}

}
