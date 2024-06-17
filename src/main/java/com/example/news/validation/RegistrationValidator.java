package com.example.news.validation;

import com.example.news.impl.UserImpl;
import com.example.news.model.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@AllArgsConstructor
public class RegistrationValidator {
    private final UserImpl userImpl;

    public void validate(@Size(max = 30) User user, BindingResult result) {
        User existingUser = userImpl.findByUsername(user.getUsername());
        User existingUserEmail = userImpl.findByEmail(user.getEmail());

        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            result.rejectValue("username", null, "Пользователь с таким логином уже зарегистрирован!");
        }

        if (user.getUsername().isEmpty()) {
            result.rejectValue("username", null, "Поле 'Логин' не может быть пустым!");
        }

        if (user.getUsername().length() > 30) {
            result.rejectValue("username", null, "Поле 'Логин' не может превышать более 30 символов!");
        }

        if (user.getPassword().length() < 6) {
            result.rejectValue("password", null, "Пароль должен содержать не менее 6 символов!");
        }

        if (user.getName().length() > 30) {
            result.rejectValue("name", null, "Поле 'Имя' не может превышать более 30 символов!");
        }

        if (user.getName().isEmpty()) {
            result.rejectValue("name", null, "Поле 'Имя' не может быть пустым!");
        }

        if (user.getName().matches(".*\\d.*")) {
            result.rejectValue("name", null, "Поле 'Имя' не может содержать в себе цифры");
        }

        if (existingUserEmail != null && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Почта уже занята!");
        }

        if (user.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Поле 'Почта' не может быть пустым!");
        }
    }
}
