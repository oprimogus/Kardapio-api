package com.kardapio.kardapioapi.user.services;

import com.kardapio.kardapioapi.user.model.UserModel;
import com.kardapio.kardapioapi.user.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    public AuthenticationService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByEmail(email);
        if (userModel.isEmpty()) throw new UsernameNotFoundException("User not found");
        return new User(
                userModel.get().getUsername(),
                "",
                true,
                true,
                true,
                true,
                userModel.get().getAuthorities());
    }
}
