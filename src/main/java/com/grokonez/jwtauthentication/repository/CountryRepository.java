package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Country;
import com.grokonez.jwtauthentication.model.CountryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findCountryByName(CountryName name);
}
