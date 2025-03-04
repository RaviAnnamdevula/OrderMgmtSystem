package com.jocata.oms.data.um.dao;

import com.jocata.oms.datamodel.um.entity.UserEntity;

import java.util.List;

public interface UserDao {
    void saveUser(UserEntity user);

    UserEntity getUserById(Integer userId);

    List<UserEntity> getAllUsers();

    void updateUser(UserEntity user);

    void deleteUser(Integer userId, Boolean flag);

    UserEntity findUserByEmailAndPassword(String email, String password);
}
