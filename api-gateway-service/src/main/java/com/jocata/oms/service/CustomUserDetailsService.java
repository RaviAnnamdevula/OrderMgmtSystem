package com.jocata.oms.service;


import com.jocata.oms.common.response.GenericResponsePayload;
import com.jocata.oms.data.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService{

    private static final String userUrl= "http://localhost:8081/users";
    private final RestTemplate restTemplate =new RestTemplate();

    public User getUserByEmail(String email) {
        String url = userUrl + "/email/" + email;
        ResponseEntity<GenericResponsePayload<User>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        System.out.println(response.getBody().getData());
        System.out.println(response.getBody().getData().getRoles());
        return response.getBody().getData();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = getUserByEmail(username);
        // user to Mono<User>
        return Mono.just(user);
    }

/*    public User getUserProfile(String email, String password) {
        String url = userUrl + "/user/profile?email=" + email + "&password=" + password;
        ResponseEntity<GenericResponsePayload<User>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }*/
/*
    public User getUserById(Integer userId) {
        String url = userUrl + "/id/" + userId;
        ResponseEntity<GenericResponsePayload<User>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        System.out.println(response.getBody().getData());
        return response.getBody().getData();
    }
*/


}
