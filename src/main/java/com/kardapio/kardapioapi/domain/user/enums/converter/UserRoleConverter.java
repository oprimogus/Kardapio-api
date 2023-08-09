package com.kardapio.kardapioapi.domain.user.enums.converter;

import com.kardapio.kardapioapi.domain.user.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(UserRole.values())
                .filter(userRole -> userRole.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
