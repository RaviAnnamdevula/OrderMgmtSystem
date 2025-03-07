package com.jocata.oms.data;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User  implements UserDetails {
    private Integer userId;
    private String fullName;
    private String email;
    private String passwordHash;

    private String phone;

    private String profilePicture;

    private String otpSecret;

    private Boolean smsEnabled = false;


    private Boolean isActive = true;

    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private Set<Address> addresses;

    private Set<Role> roles;

    private List<RefreshToken> refreshTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

/*    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
       // return NoOpPasswordEncoder.getInstance();
    }*/
    @Override
    public String getPassword() {
       return passwordHash;
        // return passwordEncoder().encode(passwordHash);
    }

    @Override
    public String getUsername() {
        return email;
    }
}
