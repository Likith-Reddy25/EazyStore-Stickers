package com.eazybytes.eazystore.controller;

import com.eazybytes.eazystore.Util.JwtUtil;
import com.eazybytes.eazystore.dto.*;
import com.eazybytes.eazystore.entity.Customer;
import com.eazybytes.eazystore.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin
            (@RequestBody LoginRequestDto loginRequestDto){
        try{
            Authentication authentication=authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
            var userDto= new UserDto();
            var loggedinuser=(Customer)authentication.getPrincipal();
            BeanUtils.copyProperties(loggedinuser, userDto);

            // Here we are populating the user details in jwt token with address, not only with name, email and mobile number
            if (loggedinuser.getAddress() != null) {
                AddressDto addressDto = new AddressDto();
                BeanUtils.copyProperties(loggedinuser.getAddress(), addressDto);
                userDto.setAddress(addressDto);
            }

            String jwtToken=jwtUtil.generateJwtToken(authentication);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),userDto,jwtToken ));
        }catch(BadCredentialsException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        } catch(AuthenticationException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
        } catch(Exception ex){
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        Optional<Customer> existingCustomer=customerRepository.findByEmailOrMobileNumber
                (registerRequestDto.getEmail(), registerRequestDto.getMobileNumber());
        if(existingCustomer.isPresent()) {
            HashMap<String, String> map = new HashMap<>();
            Customer customer = existingCustomer.get();

            if (customer.getEmail().equalsIgnoreCase(registerRequestDto.getEmail())) {
                map.put("email", "email is already registered");
            }
            if (customer.getMobileNumber().equals(registerRequestDto.getMobileNumber())) {
                map.put("mobileNumber", "mobile number is already registered");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        Customer customer= new Customer();
        BeanUtils.copyProperties(registerRequestDto, customer);
        customer.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
        customerRepository.save(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registration successful");
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                                String message) {
        return ResponseEntity.status(status).body(new LoginResponseDto(message, null,null));
    }

}
