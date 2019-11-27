package com.grokonez.jwtauthentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grokonez.jwtauthentication.model.*;

import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Country country;
    private Set<Role> roles;
    private Set<State> states;
    private Province province;
    private City city;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setCountry(country);
        user.setRoles(roles);
        return user;
    }

    public static UserDTO fromUser(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setHomeland(user.getCountry());
        userDto.setStates(user.getStates());
        userDto.setProvince(user.getProvince());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getHomeland() {
        return country;
    }

    public void setHomeland(Country country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
