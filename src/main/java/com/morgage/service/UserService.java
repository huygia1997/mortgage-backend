package com.morgage.service;


import com.morgage.common.Const;
import com.morgage.model.Pawner;
import com.morgage.model.Role;
import com.morgage.model.User;
import com.morgage.repository.PawnerRepository;
import com.morgage.repository.RoleRepository;
import com.morgage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserService {

    @PersistenceContext
    private final EntityManager em;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PawnerRepository pawnerRepository;

    public UserService(EntityManager em, UserRepository userRepository, RoleRepository roleRepository, PawnerRepository pawnerRepository) {
        this.em = em;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.pawnerRepository = pawnerRepository;
    }

    public User initUser(String name, String password) {
        User user = new User();
        Role role = new Role();
        Pawner pawner = new Pawner();
        role.setId(Const.ROLE_TYPE.USER.getRoleID());
        user.setStatus(Const.USER_STATUS.ACTIVE);
        user.setUsername(name);
        user.setPassword(password);
        user.setRole(role);
        pawner.setName(user.getUsername());
        user = userRepository.saveAndFlush(user);
        pawner.setAccountId(user.getId());
        return user;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
