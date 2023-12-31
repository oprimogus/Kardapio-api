package com.kardapio.kardapioapi.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthController {
    @GetMapping("/ping")
    @PreAuthorize("hasRole('CONSUMER')")
    public String health () {
        return "pong";
    }

    @GetMapping("/")
    public String hello () {
        return "Hello World";
    }
}
