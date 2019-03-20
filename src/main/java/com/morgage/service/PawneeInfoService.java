package com.morgage.service;

import com.morgage.model.PawneeInfo;
import com.morgage.repository.PawneeInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PawneeInfoService {
    private final PawneeInfoRepository pawneeInfoRepository;

    public PawneeInfoService(PawneeInfoRepository pawneeInfoRepository) {
        this.pawneeInfoRepository = pawneeInfoRepository;
    }

    public PawneeInfo createPawneeInfo(String name, String email, String phone, String identityNumber, String address) {
        PawneeInfo pawneeInfo = new PawneeInfo();
        pawneeInfo.setAddress(address);
        pawneeInfo.setEmail(email);
        pawneeInfo.setName(name);
        pawneeInfo.setPhoneNumber(phone);
        pawneeInfo.setIdentityNumber(identityNumber);
        pawneeInfoRepository.saveAndFlush(pawneeInfo);
        return pawneeInfo;
    }

    public List<PawneeInfo> getPawneesByEmail(String email) {
        return pawneeInfoRepository.findAllByEmail(email);
    }


}
