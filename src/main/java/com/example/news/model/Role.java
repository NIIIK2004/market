package com.example.news.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    USER("Пользователь"),
    ADMIN("Администратор");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
