package com.jocata.oms.entity;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {


    private Integer permissionId;


    private String permissionName;


    private Set<RoleEntity> roles;
}
