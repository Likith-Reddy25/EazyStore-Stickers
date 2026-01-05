package com.eazybytes.eazystore.service.impl;

import com.eazybytes.eazystore.dto.ContactRequestDto;
import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.entity.Contact;
import com.eazybytes.eazystore.entity.Product;
//import com.eazybytes.eazystore.repository.ContactRepository;
import com.eazybytes.eazystore.repository.ContactRepository;
import com.eazybytes.eazystore.repository.ProductRepository;
import com.eazybytes.eazystore.service.IContactService;
import com.eazybytes.eazystore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean savecontact(ContactRequestDto contactRequestDto) {
        try {
            Contact contact=transformToEntity(contactRequestDto);
            contact.setCreatedAt(Instant.now());
            contact.setCreatedBy(contactRequestDto.getName());
            contactRepository.save(contact);
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto){
        Contact contact= new Contact();
        BeanUtils.copyProperties(contactRequestDto, contact);
        return contact;
    }

}
