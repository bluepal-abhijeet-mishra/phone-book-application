package com.example.demo.service;

import com.example.demo.domain.Contact;
import com.example.demo.domain.User;
import com.example.demo.dto.ContactDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ContactDto createContact(ContactDto contactDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Contact contact = toEntity(contactDto);
        contact.setUser(user);
        contact = contactRepository.save(contact);
        return toDto(contact);
    }

    @Override
    public List<ContactDto> getAllContacts(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return contactRepository.findByUser(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        if (!contact.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to view this contact");
        }
        return toDto(contact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        if (!contact.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to update this contact");
        }
        contact.setName(contactDto.getName());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact = contactRepository.save(contact);
        return toDto(contact);
    }

    @Override
    public void deleteContact(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        if (!contact.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to delete this contact");
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
