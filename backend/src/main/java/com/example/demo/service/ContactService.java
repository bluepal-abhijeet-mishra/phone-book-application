package com.example.demo.service;

import com.example.demo.dto.ContactDto;

import java.util.List;

public interface ContactService {
    ContactDto createContact(ContactDto contactDto, String username);
    List<ContactDto> getAllContacts(String username);
    ContactDto getContactById(Long id, String username);
    ContactDto updateContact(Long id, ContactDto contactDto, String username);
    void deleteContact(Long id, String username);
}
