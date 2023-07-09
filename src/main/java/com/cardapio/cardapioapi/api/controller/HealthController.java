package com.cardapio.cardapioapi.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthController {
    @GetMapping("/ping")
    public String health () {
        return "pong";
    }
}
