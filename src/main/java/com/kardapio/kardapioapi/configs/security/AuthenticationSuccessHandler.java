package com.kardapio.kardapioapi.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kardapio.kardapioapi.user.enums.UserRole;
import com.kardapio.kardapioapi.user.model.UserModel;
import com.kardapio.kardapioapi.user.repository.UserRepository;
import com.kardapio.kardapioapi.user.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationSuccessHandler(TokenService tokenService,
                                        AuthenticationService authenticationService,
                                        UserRepository userRepository) {
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String email = oauthToken.getPrincipal().getAttribute("email");
            Optional<UserModel> userModel = this.userRepository.findByEmail(email);
            if (userModel.isEmpty()) {
                var newUser = new UserModel(email, UserRole.ROLE_CONSUMER);
                userRepository.save(newUser);
            }
            UserDetails userDetails = authenticationService.loadUserByUsername(email);
            String token = tokenService.createToken(userDetails);
            response.setHeader("Authorization", "Bearer " + token);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("token", token);
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);
            responseData.put("role", role);

            String json = new ObjectMapper().writeValueAsString(responseData);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

}