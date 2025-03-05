package com.jocata.oms.data;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer roleId;


    private String roleName;

    private Set<User> users;

    private Set<Permission> permissions;
}

