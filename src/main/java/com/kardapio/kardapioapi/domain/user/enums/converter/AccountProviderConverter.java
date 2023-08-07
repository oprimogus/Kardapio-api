package com.kardapio.kardapioapi.domain.user.enums.converter;

import com.kardapio.kardapioapi.domain.user.enums.AccountProvider;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class AccountProviderConverter implements AttributeConverter<AccountProvider, String> {
    @Override
    public String convertToDatabaseColumn(AccountProvider attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AccountProvider convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(AccountProvider.values())
                .filter(accountProvider -> accountProvider.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
