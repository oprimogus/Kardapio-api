package com.kardapio.kardapioapi.user.controller;

import com.kardapio.kardapioapi.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.kardapio.kardapioapi.user.dto.AuthDTO;
import com.kardapio.kardapioapi.user.dto.UserDTO;

@RestController
@RequestMapping("v1/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public UserDTO login(@RequestBody @Valid AuthDTO data) {
        return userService.login(data);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public void register(@RequestBody @Valid AuthDTO data) {
        userService.register(data);
    }
}
