package com.morgage.service;

import com.morgage.model.Pawnee;
import com.morgage.repository.PawneeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PawneeService {
    private final PawneeRepository pawneeRepository;

    public PawneeService(PawneeRepository pawneeRepository) {
        this.pawneeRepository = pawneeRepository;
    }

    public Pawnee setPawnerInfo(int accountId, String email, String phoneNumber, String avaURL) {
        Pawnee pawnee = getPawneeByAccountId(accountId);
        pawnee.setEmail(email);
        pawnee.setPhoneNumber(phoneNumber);
        pawnee.setAvaURL(avaURL);
        return pawneeRepository.save(pawnee);
    }

    public Pawnee getPawneeByAccountId(int accountId) {
        return pawneeRepository.findByAccountId(accountId);
    }

    public Integer getAccountIdFromPawnerId(int pawnerId) {
        Pawnee pawnee = new Pawnee();
        pawnee = pawneeRepository.findPawneeById(pawnerId);
        if (pawnee != null) {
            return pawnee.getAccountId();
        } else return null;
    }

    public List<Pawnee> getPawneesByEmail(String email) {
        return pawneeRepository.findAllByEmail(email);
    }
}
