package com.example.demo.service;

import com.example.demo.dto.OrganizationDto;
import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrgServiceImpl implements OrgService {
    private OrganizationRepository organizationRepository;

    private PasswordEncoder passwordEncoder;

    public OrgServiceImpl(OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(OrganizationDto organizationDto) {
        Organization organization = new Organization();
        if(organizationDto != null){
            organization.setOrg_name(organizationDto.getCompanyName());
            organization.setBusiness_email(organizationDto.getBusiness_email());
            organization.setReg_number(organizationDto.getReg_number());
            organization.setPhone_number(organizationDto.getPhone_number());
            organization.setPassword(passwordEncoder.encode(organizationDto.getPassword()));
            organization.setApproved(false);
            organizationRepository.save(organization);
        }
    }

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public String statusUpdate(Long id,String status) {
       try{
           Organization organization = organizationRepository.findById(id).get();
           if (organization != null && status.equals("true")){
               organization.setApproved(true);
               organizationRepository.save(organization);
           }else if (organization != null && status.equals("false")){
               organization.setApproved(false);
               organizationRepository.save(organization);
           }else{
               return "Invalid ID";
           }
       }catch (Exception e){
           e.printStackTrace();
           return "Error updating status";
       }
       return "Status changed successfull";
    }
}
