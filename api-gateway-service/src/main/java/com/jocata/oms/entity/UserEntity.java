package com.jocata.oms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
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

    private Set<AddressEntity> addresses;

    private Set<RoleEntity> roles;

    private List<RefreshTokenEntity> refreshTokens;
}
