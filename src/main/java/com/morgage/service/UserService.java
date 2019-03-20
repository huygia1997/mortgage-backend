package com.morgage.service;


import com.morgage.common.Const;
import com.morgage.model.Pawnee;
import com.morgage.model.Role;
import com.morgage.model.User;
import com.morgage.repository.PawneeRepository;
import com.morgage.repository.RoleRepository;
import com.morgage.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

@Service
public class UserService {

    private final EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PawneeRepository pawnerRepository;

    public UserService(EntityManager em, UserRepository userRepository, RoleRepository roleRepository, PawneeRepository pawnerRepository) {
        this.em = em;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.pawnerRepository = pawnerRepository;
    }


    public User initUser(String name, String password) {
        User user = new User();
        Role role = new Role();
        Pawnee pawnee = new Pawnee();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        role.setId(Const.ROLE_TYPE.PAWNER.getRoleID());
        user.setStatus(Const.USER_STATUS.NOT_ACTIVE);
        user.setUsername(name);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedTime(timeStamp);
        user = userRepository.saveAndFlush(user);
        if (user != null) {
            pawnee.setEmail(user.getUsername());
            pawnee.setAccountId(user.getId());
            pawnee = pawnerRepository.saveAndFlush(pawnee);
        }
        if (pawnee != null) {
            return user;
        }else return null;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User editUserInfo(String name, String password) {
        User user = userRepository.findByUsername(name);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean activeUserAccount(String token) {
        User user = userRepository.findByToken(token);
        if (user != null) {
            user.setStatus(Const.USER_STATUS.ACTIVE);
            user.setToken(null);
            save(user);
            return true;
        } else return false;
    }
    public User changePasswordWithToken(String token, String pass){
        User user = userRepository.findByToken(token);
        if (user != null) {
            user.setPassword(pass);
            user.setToken(null);
            save(user);
            return user;
        } else return user;
    }
}
