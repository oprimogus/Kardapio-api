package com.kardapio.kardapioapi.domain.user.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_CONSUMER("Consumer"),
    ROLE_EMPLOYEE("Employee"),
    ROLE_OWNER("Owner"),
    ROLE_DELIVERYMAN("DeliveryMan"),
    ROLE_ADMIN("Admin");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public String toString() {
        return value;
    }
}
