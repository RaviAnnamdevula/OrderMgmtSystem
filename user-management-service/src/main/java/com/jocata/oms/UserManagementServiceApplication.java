package com.jocata.oms;

import com.jocata.oms.data.um.dao.UserDao;
import com.jocata.oms.data.um.dao.dapImpl.UserDaoImpl;
import com.jocata.oms.datamodel.um.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class,args);
    }

   /* public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();

        UserEntity newUser = UserEntity.builder()
                .fullName("Suresh Sanem")
                .email("suresh.sanem@gmail.com")
                .passwordHash("hashed_password")
                .phone("7386782589")
                .profilePicture("9845nw8rr2n3rr2")
                .otpSecret("9878")
                .isActive(true)
                .smsEnabled(false)
                .build();

        userDao.saveUser(newUser);
        System.out.println("User saved successfully!");


        UserEntity retrievedUser = userDao.getUserById(newUser.getUserId());
        if (retrievedUser != null) {
            System.out.println("Retrieved User: " + retrievedUser.getFullName() + ", Email: " + retrievedUser.getEmail());
        } else {
            System.out.println("User not found.");
        }
    }*/
}