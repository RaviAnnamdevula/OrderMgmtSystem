package com.jocata.oms.datamodel.um.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(nullable = false, unique = true,name = "permission_name")
    private String permissionName;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles;
}
