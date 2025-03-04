package com.jocata.oms.entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    private Integer tokenId;

    private UserEntity user;


    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;
}
