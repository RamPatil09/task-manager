package com.example.demo.service;

import com.example.demo.dto.ContactDto;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void save(ContactDto contactDto) {
        try {
            Contact contact = new Contact();
            if (contactDto != null) {
                contact.setFirstname(contactDto.getFirstname());
                contact.setLastname(contactDto.getLastname());
                contact.setWorkemail(contactDto.getWorkemail());
                contact.setPhonenumber(contactDto.getPhonenumber());
                contact.setCompanyname(contactDto.getCompanyname());
                contact.setCompanysize(contactDto.getCompanysize());
                contact.setRole(contactDto.getRole());
                contact.setJoblevel(contactDto.getJoblevel());
                contact.setDiscuss(contactDto.getDiscuss());
                contact.setContacted(false);

                contactRepository.save(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public void contactedSave(Long id) {
        if (id != 0) {
            Contact byId = contactRepository.findById(id).get();
            byId.setContacted(true);
            contactRepository.save(byId);
        }
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id).get();
    }
}
