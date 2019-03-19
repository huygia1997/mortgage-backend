package com.morgage.repository;

import com.morgage.model.PawneeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PawneeInfoRepository extends JpaRepository<PawneeInfo, Integer> {
}
