package com.jocata.oms.data;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private Integer addressId;


    private User user;


    private String address;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
