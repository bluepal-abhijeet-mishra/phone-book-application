package com.example.demo.controller;

import com.example.demo.dto.ContactDto;
import com.example.demo.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto, Principal principal) {
        return ResponseEntity.ok(contactService.createContact(contactDto, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(Principal principal) {
        return ResponseEntity.ok(contactService.getAllContacts(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(contactService.getContactById(id, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long id, @RequestBody ContactDto contactDto, Principal principal) {
        return ResponseEntity.ok(contactService.updateContact(id, contactDto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id, Principal principal) {
        contactService.deleteContact(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
