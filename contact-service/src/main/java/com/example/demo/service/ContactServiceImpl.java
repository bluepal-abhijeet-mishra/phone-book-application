package com.example.demo.service;

import com.example.demo.domain.Contact;
import com.example.demo.dto.ContactDto;
import com.example.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactDto createContact(ContactDto contactDto, String userId) {
        Contact contact = toEntity(contactDto);
        contact.setUserId(userId);
        contact = contactRepository.save(contact);
        return toDto(contact);
    }

    @Override
    public List<ContactDto> getAllContacts(String userId) {
        return contactRepository.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long id, String userId) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        if (!contact.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this contact");
        }
        return toDto(contact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto, String userId) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        if (!contact.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this contact");
        }
        contact.setName(contactDto.getName());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact = contactRepository.save(contact);
        return toDto(contact);
    }

    @Override
    public void deleteContact(Long id, String userId) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        if (!contact.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this contact");
        }
        contactRepository.delete(contact);
    }

    private ContactDto toDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setName(contact.getName());
        contactDto.setPhoneNumber(contact.getPhoneNumber());
        return contactDto;
    }

    private Contact toEntity(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        return contact;
    }
}
