package com.morgage.service;

import com.morgage.model.Pawner;
import com.morgage.repository.PawnerRepository;
import org.springframework.stereotype.Service;

@Service
public class PawnerService {
    private final PawnerRepository pawnerRepository;

    public PawnerService(PawnerRepository pawnerRepository) {
        this.pawnerRepository = pawnerRepository;
    }

    public Pawner setPawnerInfo(int accountId, String email, String phoneNumber, String avaURL) {
        Pawner pawner = getPawnerByAccountId(accountId);
        pawner.setEmail(email);
        pawner.setPhoneNumber(phoneNumber);
        pawner.setAvaURL(avaURL);
        return pawnerRepository.save(pawner);
    }

    public Pawner getPawnerByAccountId(int accountId) {
        return pawnerRepository.findByAccountId(accountId);
    }

    public Integer getAccountIdFromPawnerId(int pawnerId) {
        Pawner pawner = new Pawner();
        pawner = pawnerRepository.findPawnerById(pawnerId);
        if (pawner != null) {
            return pawner.getAccountId();
        } else return null;
    }
}
