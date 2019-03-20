package com.morgage.repository;

import com.morgage.model.PawneeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawneeInfoRepository extends JpaRepository<PawneeInfo, Integer> {
    List<PawneeInfo> findAllByEmail(String email);
}
