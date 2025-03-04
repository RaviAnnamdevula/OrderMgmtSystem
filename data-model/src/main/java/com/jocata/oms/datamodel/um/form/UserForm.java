package com.jocata.oms.datamodel.um.form;

import com.jocata.oms.datamodel.um.entity.RoleEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserForm {
    private String userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String phone;
    private String profilePicture;
    private String otpSecret;
    private Boolean smsEnabled = false;
    private Boolean isActive = true;
}
