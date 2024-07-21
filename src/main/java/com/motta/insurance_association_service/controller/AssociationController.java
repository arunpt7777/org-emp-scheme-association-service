package com.motta.insurance_association_service.controller;

import java.util.List;

import com.motta.insurance_association_service.model.AssociationDTO;
import com.motta.insurance_association_service.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class AssociationController {

	@Autowired
	private AssociationService associationService;

	// create Association REST API
	@PostMapping("/associations")
	public ResponseEntity<AssociationDTO> createAssociation(@Valid @RequestBody AssociationDTO associationDTO) {
		AssociationDTO savedAssociation = associationService.createAssociation(associationDTO);
		return new ResponseEntity<>(savedAssociation, HttpStatus.CREATED);
	}

	// Retrieve Association by id REST API
	@GetMapping("/associations/{id}")
	public ResponseEntity<AssociationDTO> retrieveAssociationById(@PathVariable("id") Integer id) {
		AssociationDTO association = associationService.retrieveAssociationById(id);
		return new ResponseEntity<>(association, HttpStatus.OK);
	}

	// Retrieve Association by id using RequestParam REST API
	// For example, http://localhost:8080/association?id=10001
	@GetMapping("/association")
	public ResponseEntity<AssociationDTO> retrieveAssociationByIdRequestParam(@RequestParam Integer id) {
		AssociationDTO association = associationService.retrieveAssociationById(id);
		return new ResponseEntity<>(association, HttpStatus.OK);
	}

	// Retrieve All Associations REST API
	@GetMapping("/associations")
	public ResponseEntity<List<AssociationDTO>> retrieveAllAssociations() {
		List<AssociationDTO> associations = associationService.retrieveAllAssociations();
		return new ResponseEntity<>(associations, HttpStatus.OK);
	}

	// Update Association REST API
	@PutMapping("/associations/{id}")
	public ResponseEntity<AssociationDTO> updateAssociation(@PathVariable("id") Integer id, @RequestBody AssociationDTO associationDTO) {
		associationDTO.setId(id);
		AssociationDTO updatedAssociation = associationService.updateAssociation(associationDTO);
		return new ResponseEntity<>(updatedAssociation, HttpStatus.OK);
	}

	// Delete Association REST API
	@DeleteMapping("/associations/{id}")
	public ResponseEntity<String> deleteAssociation(@PathVariable("id") Integer id) {
		associationService.deleteAssociation(id);
		return new ResponseEntity<>("Association successfully deleted!", HttpStatus.OK);
	}


}
