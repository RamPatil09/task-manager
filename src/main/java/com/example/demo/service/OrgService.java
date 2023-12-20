package com.example.demo.service;

import com.example.demo.dto.OrganizationDto;
import com.example.demo.model.Organization;

import java.util.List;

public interface OrgService {
    void save(OrganizationDto organizationDto);
    List<Organization> findAll();

    String statusUpdate(Long id,String statusq);
}
