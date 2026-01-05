package com.eazybytes.eazystore.controller;

import com.eazybytes.eazystore.dto.ContactRequestDto;
import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.service.IContactService;
import com.eazybytes.eazystore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contacts")
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:5173")
public class ContactController {

    private final IContactService iContactService;

    @PostMapping
    public ResponseEntity<String> saveContact(@RequestBody ContactRequestDto contactRequestDto){
        iContactService.savecontact(contactRequestDto);

            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added");
    }
}
