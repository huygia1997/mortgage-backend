package com.morgage.repository;

import com.morgage.model.PawneeInfo;
import com.morgage.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PawneeInfoRepository extends JpaRepository<PawneeInfo, Integer> {
    String query = "Select * from pawnee_info pi join transaction trans on pi.id = trans.pawnee_info_id join shop sho on sho.id = trans.shop_id where pi.email = :email and sho.id = :shopId";
    @Query(value = query, nativeQuery = true)
    List<PawneeInfo> checkPawneeInfo(@Param("email") String email, @Param("shopId") int shopId);

    List<PawneeInfo> findAllByEmail(String email);
}
