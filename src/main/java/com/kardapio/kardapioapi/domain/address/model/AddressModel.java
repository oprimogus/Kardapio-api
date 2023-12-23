package com.kardapio.kardapioapi.domain.address.model;

import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.restaurant.model.RestaurantModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileModel profileModel;

    @OneToOne(mappedBy = "addressModel")
    @JoinColumn(name = "restaurant_id")
    private RestaurantModel restaurantModel;

    @NotEmpty
    @Column(nullable = false, length = 60)
    private String street;

    @NotEmpty
    @Pattern(regexp = "^(S/N|\\d{1,5}[A-Za-z]?(-\\d{1,5}[A-Za-z]?)?)$", message = "Número residencial inválido")
    @Column(nullable = false, length = 4)
    private String number;

    @Column(length = 15)
    private String complement;

    @NotEmpty
    @Column(nullable = false, length = 45)
    private String district;

    @NotEmpty
    @Column(nullable = false, length = 30)
    private String city;

    @NotEmpty
    @Column(nullable = false, length = 20)
    private String state;

    @NotEmpty
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP Inválido.")
    @Column(nullable = false, length = 10)
    private String zip;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}