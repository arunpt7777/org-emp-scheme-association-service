package com.motta.insurance_association_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motta.insurance_association_service.entity.Association;

public interface AssociationRepository extends JpaRepository<Association, Integer> {



}
