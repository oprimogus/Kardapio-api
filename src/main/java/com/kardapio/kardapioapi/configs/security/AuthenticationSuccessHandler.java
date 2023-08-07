package com.kardapio.kardapioapi.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserService userService;

    public AuthenticationSuccessHandler(TokenService tokenService,
                                        UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            var auth = oauthToken.getPrincipal();
            var iss = auth.getAttribute("iss");
            String email = auth.getAttribute("email");
            AccountProvider accountProvider = null;
            if (iss != null && iss.equals("https://accounts.google.com")) {
                accountProvider = AccountProvider.GOOGLE_ACCOUNT;
            }
            UserModel userModel = this.userService.findOrCreateByEmail(email, UserRole.ROLE_CONSUMER, accountProvider);
            String token = tokenService.createToken(userModel);
            response.setHeader("Authorization", "Bearer " + token);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("token", token);
            String json = new ObjectMapper().writeValueAsString(responseData);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

}