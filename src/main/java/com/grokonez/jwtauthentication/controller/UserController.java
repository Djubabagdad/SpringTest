package com.grokonez.jwtauthentication.controller;


import com.grokonez.jwtauthentication.dto.UserDTO;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.security.services.userServices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/users/")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUser(@PathVariable("login") final String login) {
        LOG.info("get use by login {}", login);

        Optional<User> user = userService.findByUsername(login);
        if (user == null) {
            throw new RuntimeException("user with this login not found");
        }
        return user;
    }

    @PutMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateUser(@PathVariable("login") final String login, @RequestBody final UserDTO user) {
        LOG.info("update user by login {}, with user {} params", login, user);
        return userService.updateUser(login, user);
    }


    @DeleteMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@PathVariable("login") final String login) {
        LOG.info("Removing user with login {}", login);
       return userService.delete(login);
    }
}
