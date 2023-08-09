package com.kardapio.kardapioapi.domain.user.services;

import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.exceptions.RecordNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
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

    @Transactional
    public UserModel findOrCreateByEmail(String email, UserRole role, AccountProvider accountProvider) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(email);
        if (userModelOptional.isEmpty()) {
            UserModel newUser = new UserModel(email, role, accountProvider);
            userModelOptional = Optional.of(userRepository.save(newUser));
            userRepository.save(newUser);
        }
        return userModelOptional.get();
    }

    public UserModel getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Optional<?> optionalPrincipal) {
            if (optionalPrincipal.isPresent() && optionalPrincipal.get() instanceof UserModel) {
                return (UserModel) optionalPrincipal.get();
            } else {
                throw new RecordNotFoundException("User not found or wrong type");
            }
        } else {
            throw new RecordNotFoundException("Principal is not an Optional");
        }
    }
}
