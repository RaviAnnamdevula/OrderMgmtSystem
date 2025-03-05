package com.jocata.oms.data;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {


    private Integer permissionId;


    private String permissionName;


    private Set<Role> roles;
}
