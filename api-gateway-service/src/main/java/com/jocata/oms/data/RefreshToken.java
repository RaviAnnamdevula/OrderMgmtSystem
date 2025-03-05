package com.jocata.oms.data;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    private Integer tokenId;

    private User user;


    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;
}
