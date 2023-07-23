package com.kardapio.kardapioapi.user.services;

import com.kardapio.kardapioapi.configs.security.TokenService;
import com.kardapio.kardapioapi.exceptions.RecordConflictException;
import com.kardapio.kardapioapi.exceptions.RecordNotFoundException;
import com.kardapio.kardapioapi.user.model.UserModel;
import com.kardapio.kardapioapi.user.repository.UserRepository;
import com.kardapio.kardapioapi.user.dto.AuthDTO;
import com.kardapio.kardapioapi.user.dto.UserDTO;
import com.kardapio.kardapioapi.user.enums.UserRole;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public UserService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public void register (@Valid AuthDTO data) {
        Optional<UserModel> userModel = this.userRepository.findByEmail(data.email());
        if (userModel.isPresent()) {
            throw new RecordConflictException("Email já cadastrado.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUserModel = new UserModel(data.email(), encryptedPassword, UserRole.ROLE_CONSUMER);
        this.userRepository.save(newUserModel);
    }

    public UserDTO login (@Valid AuthDTO data) {
        Optional<UserModel> userModel = this.userRepository.findByEmail(data.email());
        if (userModel.isEmpty()) {
            throw new RecordNotFoundException("Usuario nao encontrado");
        }
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.createToken((UserDetails) auth.getPrincipal());
        return new UserDTO(token, userModel.get().getRole());
    }
}
