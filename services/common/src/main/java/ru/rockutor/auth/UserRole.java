package ru.rockutor.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Роли пользователей в Rockutor:
 * - Сотрудник
 * - Инспектор
 **/
public enum UserRole {
    EMPLOYEE,
    INSPECTOR;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", this.name())));
    }
}
