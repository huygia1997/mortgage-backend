package com.morgage.service;

import com.morgage.model.PawneeInfo;
import com.morgage.repository.PawneeInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class PawneeInfoService {
    private final PawneeInfoRepository pawneeInfoRepository;

    public PawneeInfoService(PawneeInfoRepository pawneeInfoRepository) {
        this.pawneeInfoRepository = pawneeInfoRepository;
    }

    public PawneeInfo createOneTimePawnee(String name, String email, String phone, String indedifyNumber, String address) {
        PawneeInfo pawneeInfo = new PawneeInfo();
        pawneeInfo.setAddress(address);
        pawneeInfo.setEmail(email);
        pawneeInfo.setName(name);
        pawneeInfo.setPhoneNumber(phone);
        pawneeInfo.setIdentityNumber(indedifyNumber);
        pawneeInfoRepository.saveAndFlush(pawneeInfo);
        return pawneeInfo;
    }
}
