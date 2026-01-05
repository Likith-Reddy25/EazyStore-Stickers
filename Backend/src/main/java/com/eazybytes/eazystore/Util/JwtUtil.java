package com.eazybytes.eazystore.Util;

import com.eazybytes.eazystore.Constants.ApplicationConstants;
import com.eazybytes.eazystore.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class JwtUtil {
    private final Environment env;

    public String generateJwtToken(Authentication authentication){
        String jwt = "";
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Customer fetchedCustomer = (Customer) authentication.getPrincipal();
        jwt = Jwts.builder().issuer("Eazy Store").subject("JWT Token")
                .claim("username", fetchedCustomer.getName())
                .claim("email", fetchedCustomer.getEmail())
                .claim("mobileNumber", fetchedCustomer.getMobileNumber())
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 24 * 60 * 60 ))
                .signWith(secretKey).compact();
        return jwt;
    }

}
