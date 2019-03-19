package com.morgage.repository;

import com.morgage.model.Pawner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawnerRepository extends JpaRepository<Pawner, Integer> {
    Pawner findByAccountId(int accountId);


    Pawner findPawnerById(int id);

    List<Pawner> findAllByEmailContaining(String email);
}
