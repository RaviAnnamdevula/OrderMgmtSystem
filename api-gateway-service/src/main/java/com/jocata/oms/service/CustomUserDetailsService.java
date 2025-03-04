package com.jocata.oms.service;


import com.jocata.oms.common.response.GenericResponsePayload;
import com.jocata.oms.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService {
    private static final String userUrl= "http://localhost:8081/users";

    private RestTemplate restTemplate =new RestTemplate();


    public UserEntity getUserById(Integer userId) {
        String url = userUrl + "/" + userId;
        ResponseEntity<GenericResponsePayload<UserEntity>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public UserEntity getUserProfile(String email, String password) {
        String url = userUrl + "/user/profile?email=" + email + "&password=" + password;
        ResponseEntity<GenericResponsePayload<UserEntity>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }


}
