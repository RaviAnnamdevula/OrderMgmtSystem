package com.jocata.oms.service.Impl;

import com.jocata.oms.data.um.dao.UserDao;
import com.jocata.oms.datamodel.um.entity.RoleEntity;
import com.jocata.oms.datamodel.um.entity.UserEntity;
import com.jocata.oms.datamodel.um.form.UserForm;
import com.jocata.oms.exception.UserNotFoundException;
import com.jocata.oms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity createUser(UserForm userForm) {
        UserEntity user = UserEntity.builder()
                .fullName(userForm.getFullName())
                .email(userForm.getEmail())
                .passwordHash(userForm.getPasswordHash())
                .phone(userForm.getPhone())
                .profilePicture(userForm.getProfilePicture())
                .otpSecret(userForm.getOtpSecret())
                .smsEnabled(userForm.getSmsEnabled())
                .isActive(userForm.getIsActive())
                .build();
        userDao.saveUser(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserById(Integer userId) {
        UserEntity user = userDao.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        return user;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with Email: " + email + " not found");
        }
        // Explicitly fetch the addresses
        if (user.getAddresses() != null) {
            user.getAddresses().size();
        }
        return user;
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public UserEntity getUserByEmailAndPassword(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password);
    }




    @Override
    public UserEntity updateUser(UserForm userForm) {
        UserEntity user = userDao.getUserById(Integer.valueOf(userForm.getUserId()));
        if (user != null) {
            user.setFullName(userForm.getFullName());
            user.setEmail(userForm.getEmail());
            user.setPasswordHash(userForm.getPasswordHash());
            user.setPhone(userForm.getPhone());
            user.setProfilePicture(userForm.getProfilePicture());
            user.setOtpSecret(userForm.getOtpSecret());
            user.setSmsEnabled(userForm.getSmsEnabled());
            user.setIsActive(userForm.getIsActive());
            userDao.updateUser(user);
        }
        return user;
    }

    @Override
    public void deleteUser(Integer userId , Boolean flag) {
        userDao.deleteUser(userId , flag);
    }

}
