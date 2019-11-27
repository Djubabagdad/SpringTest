package com.grokonez.jwtauthentication.security.services.userServices;


import com.grokonez.jwtauthentication.dto.UserDTO;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.model.*;
import com.grokonez.jwtauthentication.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service

public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CountryRepository countryRepository, ProvinceRepository provinceRepository, CityRepository cityRepository, StateRepository stateRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public ResponseEntity<String> delete(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User with such username not found"));

        userRepository.delete(user);
        log.info("IN delete - user with login: {} successfully deleted", username);
        return ResponseEntity.ok().body("User deleted successfully!");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(final String login, final UserDTO user) {
        User currentUser = userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User result = user.toUser();

        if (user.getPassword() != null) {
            result.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            throw new RuntimeException("Password is incorrect");
        }

        result.setUsername(user.getUsername());
        result.setRoles(currentUser.getRoles());

        userRepository.delete(currentUser);

        return ResponseEntity.ok().body("User updated successfully!");
    }

    @Override
    @Transactional
    public ResponseEntity<String> saveUser(final SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(signUpRequest.getCountry().equals(CountryName.Canada.name())){
            Country country =  countryRepository.findCountryByName(CountryName.Canada)
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            user.setCountry(country);

            Province province = provinceRepository.findProvinceByName(signUpRequest.getProvince())
                    .orElseThrow(() -> new RuntimeException("Province not found"));
            user.setProvince(province);

            City city = cityRepository.findCityByName(signUpRequest.getCity()).
                    orElseThrow(() -> new RuntimeException("City not found"));
            user.setCity(city);

        }else if(signUpRequest.getCountry().equals(CountryName.USA.name())){
            Set<String> strStates = signUpRequest.getStates();
            Set<State> states = new HashSet<>();

            Country country =  countryRepository.findCountryByName(CountryName.USA)
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            user.setCountry(country);

            strStates.forEach(state -> {
                State resultState = stateRepository.findStateByName(state)
                        .orElseThrow(() -> new RuntimeException("State not found"));
                states.add(resultState);
            });
            user.setStates(states);

        }else{
            throw new RuntimeException("Country is null");
        }

        strRoles.forEach(role -> {
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
                }
        );

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body("User registered successfully!");
    }

}
