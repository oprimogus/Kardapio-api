package com.kardapio.kardapioapi.domain.user.enums;

public enum AccountProvider {

    GOOGLE_ACCOUNT("Google"),
    APPLE_ACCOUNT("Apple"),
    META_ACCOUNT("Meta");

    private final String value;

    AccountProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
