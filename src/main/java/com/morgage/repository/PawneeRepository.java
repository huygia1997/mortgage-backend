package com.morgage.repository;

import com.morgage.model.Pawnee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawneeRepository extends JpaRepository<Pawnee, Integer> {
    Pawnee findByAccountId(int accountId);


    Pawnee findPawneeById(int id);

    List<Pawnee> findAllByEmail(String email);
}
