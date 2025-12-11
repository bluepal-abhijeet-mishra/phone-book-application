package com.example.demo.service;

import com.example.demo.dto.ContactDto;

import java.util.List;

public interface ContactService {
    ContactDto createContact(ContactDto contactDto, String userId);
    List<ContactDto> getAllContacts(String userId);
    ContactDto getContactById(Long id, String userId);
    ContactDto updateContact(Long id, ContactDto contactDto, String userId);
    void deleteContact(Long id, String userId);
}
