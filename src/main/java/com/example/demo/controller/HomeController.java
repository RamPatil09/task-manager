package com.example.demo.controller;

import com.example.demo.dto.ContactDto;
import com.example.demo.model.Contact;
import com.example.demo.service.ContactServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    private ContactServiceImpl contactService;

    public HomeController(ContactServiceImpl contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contactDto", contactDto);
        return "/contact";
    }

    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about(){
        return "/about";
    }

    @RequestMapping(value = "/savecontact", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute ContactDto contactDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/contact";
        }
        contactService.save(contactDto);
        return "redirect:/index";
    }

    @RequestMapping(value = "/getallcontact", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Contact> all = contactService.findAll();
        model.addAttribute("contactDto", all);
        return "admin/contact_list";
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public String contacted(@PathVariable Long id) {
        if (id != 0) {
            contactService.contactedSave(id);
        }
        return "redirect:/getallcontact";
    }
}
