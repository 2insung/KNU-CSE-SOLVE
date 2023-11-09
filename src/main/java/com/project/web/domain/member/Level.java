package com.project.web.domain.member;

public enum Level {
    LEVEL_USER("유저", 1), LEVEL_MANAGER("매니저", 2), LEVEL_ADMIN("어드민", 3);

    private final String name;
    private final Integer value;

    Level(String name, Integer value) {
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
