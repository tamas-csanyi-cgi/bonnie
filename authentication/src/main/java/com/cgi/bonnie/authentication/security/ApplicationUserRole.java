package com.cgi.bonnie.authentication.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cgi.bonnie.authentication.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ASSEMBLER(Set.of(ASSEMBLER_CLAIM, ASSEMBLER_RELEASE, ASSEMBLER_FINISH, ASSEMBLER_SHIP)),
    ADMIN(Collections.EMPTY_SET);

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
