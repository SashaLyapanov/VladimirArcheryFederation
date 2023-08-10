package com.example.kursachrps.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.SPORTSMEN_READ, Permission.SPORTSMAN_UPDATE, Permission.SPORTSMAN_WRITE, Permission.SPORTSMAN_DELETE)),
    SPORTSMAN(Set.of(Permission.SPORTSMEN_READ, Permission.SPORTSMAN_UPDATE)),
    COACH(Set.of(Permission.SPORTSMEN_READ, Permission.SPORTSMAN_WRITE, Permission.SPORTSMAN_UPDATE)),
    JUDGE(Set.of(Permission.SPORTSMEN_READ, Permission.SPORTSMAN_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() { return permissions; }

    //Реализация меппера для перевода пермишена в SimpleGrantedAuthority, который необходим для SpringSecurity
    //GrantedAuthority будет использовать данный SimpleGrantedAuthority для хранения коллекции прав пользователя
    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
