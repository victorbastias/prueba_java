package com.ey.util;

public enum RolEnum {
    ADMIN("Admin"),
    NORMAL("Normal");

    private final String displayName;

    RolEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
