package com.kardapio.kardapioapi.domain.profile.model;

import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "profile")
public class ProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "profileModel")
    private UserModel userModel;

    @NotEmpty
    @Column(nullable = false, length = 25)
    private String name;

    @NotEmpty
    @Column(nullable = false, length = 75)
    private String lastName;

    @NotEmpty
    @CPF
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotEmpty
    @Column(nullable = false, length = 14)
    private String phone;

    @Valid
    @OneToMany(mappedBy = "profileModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AddressModel> address = new HashSet<>();

    @Column(nullable = false, columnDefinition = "timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AddressModel> getAddress() {
        return address;
    }

    public void setAddress(Set<AddressModel> address) {
        this.address = address;
    }
}
