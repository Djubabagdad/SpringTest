package com.grokonez.jwtauthentication.security.services.userServices;

import com.grokonez.jwtauthentication.dto.UserDTO;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    ResponseEntity<String> delete(String login);

    ResponseEntity<String> updateUser(String login, UserDTO user);

    ResponseEntity<String> saveUser(SignUpForm user);
}
