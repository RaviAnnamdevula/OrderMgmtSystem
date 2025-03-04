package com.jocata.oms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    private Integer roleId;


    private String roleName;

    private Set<UserEntity> users;

    private Set<PermissionEntity> permissions;
}

