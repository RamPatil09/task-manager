package com.example.demo.repository;

import com.example.demo.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    @Query(value = "SELECT * FROM organization o WHERE o.status = false", nativeQuery = true)
    List<Organization> findByApproved();

}
