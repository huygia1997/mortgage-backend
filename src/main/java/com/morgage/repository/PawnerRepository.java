package com.morgage.repository;

import com.morgage.model.Pawner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PawnerRepository extends JpaRepository<Pawner, Integer> {
    Pawner findByAccountId(int accountId);
}
