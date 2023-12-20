package com.example.demo.service;

import com.example.demo.dto.ContactDto;
import com.example.demo.model.Contact;

import java.util.List;

public interface ContactService {
    void save(ContactDto contactDto);

    List<Contact> findAll();

    void contactedSave(Long id);

    Contact findById(Long id);
}
