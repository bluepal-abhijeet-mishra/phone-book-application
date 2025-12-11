package com.example.demo.controller;

import com.example.demo.dto.ContactDto;
import com.example.demo.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto, @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(contactService.createContact(contactDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(contactService.getAllContacts(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(contactService.getContactById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long id, @RequestBody ContactDto contactDto, @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(contactService.updateContact(id, contactDto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id, @RequestHeader("X-User-Id") String userId) {
        contactService.deleteContact(id, userId);
        return ResponseEntity.noContent().build();
    }
}
