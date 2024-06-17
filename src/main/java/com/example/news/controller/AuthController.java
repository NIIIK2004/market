package com.example.news.controller;

import com.example.news.impl.UserImpl;
import com.example.news.model.User;
import com.example.news.repo.UserRepo;
import com.example.news.validation.RegistrationValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserImpl userImpl;
    private final RegistrationValidator registrationValidator;

    @GetMapping("/registration")
    public String regPage(Model model) {
        model.addAttribute("user", new User());
        return "reg";
    }

    @PostMapping("/registration/save")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        registrationValidator.validate(user, result);

        try {
            if (result.hasErrors()) {
                model.addAttribute("errors", result.getAllErrors());
                model.addAttribute("user", user);
                return "reg";
            }
            userImpl.registerAuthUser(user, redirectAttributes);
            return "auth";
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", e.getMessage());
            model.addAttribute("user", user);
            return "reg";
        }
    }


    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "auth";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        if (username.equals("admin") && password.equals("password")) {
            return "redirect:/";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        userImpl.logout(request, response);
        return "redirect:/login?logout";
    }

}