package com.dbd.nanal.model;

import lombok.Getter;

@Getter
public enum UserRoleEntity {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRoleEntity(String value) {
        this.value = value;
    }

    private final String value;
}
