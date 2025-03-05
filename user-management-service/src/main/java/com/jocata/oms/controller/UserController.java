package com.jocata.oms.controller;

import com.jocata.oms.common.request.GenericRequestPayload;
import com.jocata.oms.common.response.GenericResponsePayload;
import com.jocata.oms.common.util.ResponseBuilder;
import com.jocata.oms.datamodel.um.entity.UserEntity;
import com.jocata.oms.datamodel.um.form.UserForm;
import com.jocata.oms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponsePayload<UserEntity>> createUser(@RequestBody GenericRequestPayload<UserForm> request) {
        UserEntity user = userService.createUser(request.getData());
        return ResponseEntity.ok(ResponseBuilder.buildResponse(user, "User created successfully", HttpStatus.OK));
    }

    @GetMapping("/id/{userId}")
    public GenericResponsePayload<UserEntity> getUserById(@PathVariable Integer userId) {
        UserEntity user = userService.getUserById(userId);
        return buildResponse(user, "User fetched successfully");
    }

    @GetMapping("/email/{email}")
    public GenericResponsePayload<UserEntity> getUserByEmail(@PathVariable String email) {
        UserEntity user = userService.getUserByEmail(email);
        return buildResponse(user, "User fetched successfully");
    }

    @GetMapping("/user/profile")
    public ResponseEntity<GenericResponsePayload<UserEntity>> getUserProfile(
            @RequestParam String email,
            @RequestParam String password) {

        UserEntity user = userService.getUserByEmailAndPassword(email, password);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBuilder.buildResponse(null, "Invalid email or password", HttpStatus.NOT_FOUND));
        }

        return ResponseEntity.ok(ResponseBuilder.buildResponse(user, "User profile fetched successfully", HttpStatus.OK));
    }

    @GetMapping("/admin/all")
    public GenericResponsePayload<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return buildResponse(users, "All users fetched successfully");
    }

    @PutMapping("/update")
    public GenericResponsePayload<UserEntity> updateUser(@RequestBody GenericRequestPayload<UserForm> request) {
        UserEntity updatedUser = userService.updateUser(request.getData());
        return buildResponse(updatedUser, "User updated successfully");
    }

    @DeleteMapping("/delete/{userId}")
    public GenericResponsePayload<String> deleteUser(@PathVariable Integer userId, @RequestParam Boolean flag) {
        userService.deleteUser(userId , flag);
        return buildResponse("User deleted successfully", "User deleted");
    }

    private <T> GenericResponsePayload<T> buildResponse(T data, String message) {
        GenericResponsePayload<T> response = new GenericResponsePayload<>();
        response.setRequestId(generateRequestId());
        response.setTimeStamp(getCurrentTimestamp());
        response.setStatusCode(200);
        response.setStatusMessage(message);
        response.setData(data);
        return response;
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().toString();
    }
}
