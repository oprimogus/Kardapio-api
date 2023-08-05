package com.kardapio.kardapioapi.domain.address.model;

import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 60)
    private String street;

    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 20)
    private String city;

    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 20)
    private String state;

    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 15)
    private String zip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileModel profileModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }
}