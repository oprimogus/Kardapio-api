package com.kardapio.kardapioapi.domain.user.services;

import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.exceptions.database.RecordNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private static final String ERROR_MESSAGE = "Usuário não encontrado.";
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = Optional.of(this.userRepository.findByEmail(email)).get()
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(
                userModel.getUsername(),
                "",
                true,
                true,
                true,
                true,
                userModel.getAuthorities());
    }

    @Transactional
    public UserModel findOrCreateByEmail(String email, UserRole role, AccountProvider accountProvider) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserModel newUser = new UserModel(email, role, accountProvider);
                    return userRepository.save(newUser);
                });
    }

    public UserModel getUserByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Optional<?>)) {
            throw new RecordNotFoundException("Principal is not an Optional");
        }
        return ((Optional<?>) principal)
                .filter(UserModel.class::isInstance)
                .map(UserModel.class::cast)
                .orElseThrow(RecordNotFoundException::new);
    }
}
