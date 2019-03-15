package com.morgage.repository;

import com.morgage.model.OneTimePawnee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimePawneeRepository extends JpaRepository<OneTimePawnee, Integer> {
}
