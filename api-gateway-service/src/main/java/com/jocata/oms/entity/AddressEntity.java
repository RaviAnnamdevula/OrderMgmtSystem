package com.jocata.oms.entity;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    private Integer addressId;


    private UserEntity user;


    private String address;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
