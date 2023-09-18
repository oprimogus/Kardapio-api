package com.kardapio.kardapioapi.utils;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;


public class FakeObjectsFactory {

    public static UserModel getDefaultUserModel(boolean withProfile) {
        UserModel user = new UserModel("john@example.com", UserRole.ROLE_CONSUMER, AccountProvider.GOOGLE_ACCOUNT);
        user.setId(UUID.fromString("c4fc6b9b-05c2-4630-a509-f03b289d6440"));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        if (withProfile) {
            ProfileModel profile = new ProfileModel();
            profile.setId(1L);
            profile.setName("John");
            profile.setLastName("Doe");
            profile.setCpf("24223457861");
            profile.setPhone("13982289714");
            profile.setUserModel(user);
            profile.setAddress(null);
            user.setProfileModel(profile);
        }
        return user;
    }

    public static UserModel getCustomUserModel(String email, UserRole userRole, AccountProvider accountProvider) {
        UserModel user = new UserModel(email, userRole, accountProvider);
        user.setId(UUID.randomUUID());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static UserDetails getDefaultUserDetails() {
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(UserRole.ROLE_CONSUMER.getValue()));
        return new User(
                "john@example.com",
                "",
                true,
                true,
                true,
                true,
                authorities);
    }

    public static UserDetails getCustomUserDetails(String email, UserRole userRole) {
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole.getValue()));
        return new User(
                email,
                "",
                true,
                true,
                true,
                true,
                authorities);
    }

    public static ProfileDTO getDefaultProfileDto(boolean withAddress) {
        Set<AddressDTO> addresses;
        if (withAddress) {
            addresses = Set.of(
                    new AddressDTO(
                            "Rua A",
                            "100",
                            "Apto 10",
                            "Centro",
                            "Guarujá",
                            "SP",
                            "12345-123"
                    ),
                    new AddressDTO(
                            "Rua B",
                            "101",
                            "Apto 11",
                            "Centro",
                            "Guarujá",
                            "SP",
                            "12345-124"
                    )
            );
        } else {
            addresses = Collections.emptySet();
        }
        return new ProfileDTO(
                "John",
                "Doe",
                "24223457861",
                "13982289714",
                "",
                addresses
        );
    }
}
