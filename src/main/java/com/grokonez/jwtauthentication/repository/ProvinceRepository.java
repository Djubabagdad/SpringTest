package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Optional<Province> findProvinceByName(String name);
}
