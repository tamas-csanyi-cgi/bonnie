package com.cgi.hexagon.authentication.security;

public enum ApplicationUserPermission {
    ASSEMBLER_CLAIM("assembler:claim"),
    ASSEMBLER_RELEASE("assembler:release"),
    ASSEMBLER_FINISH("assembler:finish"),
    ASSEMBLER_SHIP("assembler:ship");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
