package com.jocata.oms.service;

import com.jocata.oms.datamodel.um.entity.UserEntity;
import com.jocata.oms.datamodel.um.form.UserForm;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserForm userForm);

    UserEntity getUserById(Integer userId);


    List<UserEntity> getAllUsers();

    UserEntity updateUser(UserForm userForm);

    void deleteUser(Integer userId , Boolean flag);

    UserEntity getUserByEmailAndPassword(String email, String password);
}
