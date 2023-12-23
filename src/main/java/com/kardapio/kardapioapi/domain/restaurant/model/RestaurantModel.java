package com.kardapio.kardapioapi.domain.restaurant.model;


import com.kardapio.kardapioapi.domain.Constants;
import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Entity(name = "restaurant")
public class RestaurantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressModel addressModel;

    @NotEmpty
    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @NotEmpty
    @Column(nullable = false, length = 25, unique = true)
    private String slug;

    @NotEmpty
    @CNPJ
    @Column(nullable = false, length = 25, unique = true)
    private String cnpj;

    @NotEmpty
    @Pattern(regexp = "^\\+55\\d{11}$", message = "Número de telefone inválido.")
    @Column(length = 14)
    private String phone;

    @NotEmpty
    @DecimalMax(value = Constants.MAX_SCORE_VALUE_STRING,
            message = "O score não pode ser maior que " + Constants.MAX_SCORE_VALUE_STRING + "estrelas.")
    @DecimalMin(value = "0.00",
            message = "O score não pode ser menor que " + Constants.MAX_SCORE_VALUE_STRING + "estrelas.")
    @Column(nullable = false)
    private float score;

    @NotEmpty
    @DecimalMax(value = Constants.MAX_DELIVERY_DISTANCE, message = "A distância máxima de entrega não pode ultrapassar ")

    @Column(nullable = false, columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug() {
        this.slug = name.toLowerCase().replace(" ", "");
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
