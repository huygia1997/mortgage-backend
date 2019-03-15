package com.morgage.service;

import com.morgage.model.OneTimePawnee;
import com.morgage.repository.OneTimePawneeRepository;
import org.springframework.stereotype.Service;

@Service
public class OneTimePawneeService {
    private final OneTimePawneeRepository oneTimePawneeRepository;

    public OneTimePawneeService(OneTimePawneeRepository oneTimePawneeRepository) {
        this.oneTimePawneeRepository = oneTimePawneeRepository;
    }

    public OneTimePawnee createOneTimePawnee(String name, String email, String phone, int transactionId, String address) {
        OneTimePawnee oneTimePawnee = new OneTimePawnee();
        oneTimePawnee.setAddress(address);
        oneTimePawnee.setEmail(email);
        oneTimePawnee.setName(name);
        oneTimePawnee.setPhoneNumber(phone);
        oneTimePawnee.setTransactionId(transactionId);
        oneTimePawneeRepository.saveAndFlush(oneTimePawnee);
        return oneTimePawnee;
    }
}
