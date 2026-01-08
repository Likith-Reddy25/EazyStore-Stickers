package com.eazybytes.eazystore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/check")
public class CheckController {

    public String test(){
        return "Deployment Successful";
    }
}
