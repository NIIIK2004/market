package com.example.news.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/js/**", "/css/**", "/font/**", "/images/**", "/static/**")
                .permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                                .and()
                )
                .rememberMe()
                .key("my-super-secret-key")
                .tokenValiditySeconds(600200)
                .and()
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new RequestHeaderRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}


























