package com.insung.knucsesolve.domain.member;

public enum Role {
    ROLE_USER("유저", 1), ROLE_ADMIN("어드민", 2);

    private final String name;
    private final Integer value;

    Role(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
