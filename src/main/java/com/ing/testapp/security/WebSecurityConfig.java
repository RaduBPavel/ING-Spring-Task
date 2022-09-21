package com.ing.testapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.GET).hasRole("USER")
                        .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .and()
                    .authorizeRequests()
                        .anyRequest().authenticated()
                .and()
                    .csrf().disable();


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails basicUser = User
                .withUsername("basic")
                .password("password")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("admin")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(List.of(basicUser, admin));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
