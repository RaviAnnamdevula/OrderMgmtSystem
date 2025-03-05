package com.jocata.oms.config;

import com.jocata.oms.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.spi.CurrencyNameProvider;

@Configuration
public class UserDetailsConfig {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

   /* @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService( customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }*/

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
       // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
/*
    @Bean
    public MapReactiveUserDetailsService  userDetailsService() {
        UserDetails admin = User.builder()
                .username("suresh")
                .password(passwordEncoder().encode("admin123")) // ✅ Secure Password
                .roles("ADMIN") // ✅ Assigns ROLE_ADMIN
                .build();

        UserDetails user = User.builder()
                .username("susan")
                .password(passwordEncoder().encode("user123")) // ✅ Secure Password
                .roles("USER") // ✅ Assigns ROLE_USER
                .build();
        return new MapReactiveUserDetailsService(user, admin);
    }
*/