package com.morgage.repository;

import com.morgage.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findCityByCityName(String name);
}
