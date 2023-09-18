package com.kardapio.kardapioapi.domain.user;

import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import com.kardapio.kardapioapi.utils.FakeObjectsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return userDetails with success")
    void testLoadByUsername() {
        UserDetails userDetailsExpected = FakeObjectsFactory.getDefaultUserDetails();
        UserModel userModelExpected = FakeObjectsFactory.getDefaultUserModel(true);
        Mockito.when(userRepository
                .findByEmail(userDetailsExpected.getUsername()))
                .thenReturn(Optional.of(userModelExpected));

        UserDetails userReceived = this.userService.loadUserByUsername(userDetailsExpected.getUsername());
        Assertions.assertNotNull(userReceived);
        Assertions.assertEquals(userDetailsExpected, userReceived);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user is not found")
    void testLoadByUsernameFail() {
        UserDetails userDetailsExpected = FakeObjectsFactory.getDefaultUserDetails();
        String email = userDetailsExpected.getUsername();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            this.userService.loadUserByUsername(email);
        });
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should return UserModel founded in database")
    void testFindOrCreateByEmail() {
        String email = "john@example.com";
        UserRole userRole = UserRole.ROLE_CONSUMER;
        AccountProvider accountProvider = AccountProvider.GOOGLE_ACCOUNT;
        UserModel userExpected = new UserModel(email, userRole, accountProvider);

        Mockito.when(this.userRepository.findByEmail(email))
                .thenReturn(Optional.of(userExpected));

        UserModel userModel = this.userService.findOrCreateByEmail(email, userRole, accountProvider);

        Mockito.verify(this.userRepository, times(1)).findByEmail(anyString());
        Mockito.verify(this.userRepository, times(0)).save(any(UserModel.class));

        Assertions.assertNotNull(userModel);
        Assertions.assertEquals(userModel, userExpected);
    }

    @Test
    @DisplayName("Should return a created UserModel")
    void testFindOrCreateByEmail2() {
        String email = "john@example.com";
        UserRole userRole = UserRole.ROLE_CONSUMER;
        AccountProvider accountProvider = AccountProvider.GOOGLE_ACCOUNT;
        UserModel userExpected = new UserModel(email, userRole, accountProvider);

        Mockito.when(this.userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        Mockito.when(this.userRepository.save(any(UserModel.class)))
                .thenReturn(userExpected);

        UserModel userModel = this.userService.findOrCreateByEmail(email, userRole, accountProvider);

        Mockito.verify(this.userRepository, times(1)).findByEmail(anyString());
        Mockito.verify(this.userRepository, times(1)).save(any(UserModel.class));

        Assertions.assertNotNull(userModel);
        Assertions.assertEquals(userModel.getEmail(), userExpected.getEmail());
        Assertions.assertEquals(userModel.getRole(), userExpected.getRole());
        Assertions.assertEquals(userModel.getAccountProvider(), userExpected.getAccountProvider());
    }

    @Test
    @DisplayName("Should return UserModel from jwt authentication")
    void testGetUserByAuth() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        UserModel userExpected = FakeObjectsFactory.getDefaultUserModel(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(Optional.of(userExpected));
        SecurityContextHolder.setContext(securityContext);

        UserModel user = this.userService.getUserByAuth();

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user, userExpected);
        Assertions.assertEquals(user.getEmail(), userExpected.getEmail());
        Assertions.assertEquals(user.getRole(), userExpected.getRole());
        Assertions.assertEquals(user.getAccountProvider(), userExpected.getAccountProvider());
    }
}
