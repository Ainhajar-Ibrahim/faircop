package com.emse.spring.faircorp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
public class SpringSecurityConfig  {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";


    @Bean
    public UserDetailsService userDetailsService() {
        // We create a password encoder
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user").password(encoder.encode("myPassword")).roles(ROLE_USER).build()
        );
        manager.createUser(
                User.withUsername("admin").password(encoder.encode("adminPassword")).roles(ROLE_ADMIN).build()
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }
    @Bean
    @Order(1) // (1)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .antMatcher("/api/**")
                .authorizeRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }

}