package com.kardapio.kardapioapi.domain.user.model;

import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import com.kardapio.kardapioapi.domain.user.enums.converter.AccountProviderConverter;
import com.kardapio.kardapioapi.domain.user.enums.converter.UserRoleConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "_user")
public class UserModel implements UserDetails {
    public UserModel() {
    }

    public UserModel(String email, UserRole role, AccountProvider accountProvider) {
        this.email = email;
        this.role = role;
        this.accountProvider = accountProvider;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private transient ProfileModel profileModel;

    @NotEmpty
    @Email
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column()
    private String password;

    @Convert(converter = UserRoleConverter.class)
    @Column(length = 15, nullable = false)
    private UserRole role;

    @Convert(converter = AccountProviderConverter.class)
    @Column(length = 15, nullable = false)
    private AccountProvider accountProvider;

    @Column(nullable = false, columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountProvider getAccountProvider() {
        return accountProvider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().getValue()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }
}
