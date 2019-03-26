package com.morgage.service;


import com.morgage.common.Const;
import com.morgage.model.*;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {

    private final EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PawneeRepository pawnerRepository;
    private final RateShopRepository rateShopRepository;
    private final ShopRepository shopRepository;

    public UserService(EntityManager em, UserRepository userRepository, RoleRepository roleRepository, PawneeRepository pawnerRepository, RateShopRepository rateShopRepository, ShopRepository shopRepository) {
        this.em = em;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.pawnerRepository = pawnerRepository;
        this.rateShopRepository = rateShopRepository;
        this.shopRepository = shopRepository;
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
            pawnee.setName(user.getUsername());
            pawnee = pawnerRepository.saveAndFlush(pawnee);
        }
        if (pawnee != null) {
            return user;
        } else return null;
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

    public User changePasswordWithToken(String token, String pass) {
        User user = userRepository.findByToken(token);
        if (user != null) {
            user.setPassword(pass);
            user.setToken(null);
            save(user);
            return user;
        } else return user;
    }

    public Integer rateShop(int accountId, int shopId, int rate) {
        RateShop rateShop = new RateShop();
        Shop shop = shopRepository.findById(shopId);
        Pawnee pawnee = pawnerRepository.findByAccountId(accountId);
        if (pawnee != null && shop != null) {
            if (rateShopRepository.findByShop_IdAndPawnee_Id(shopId, pawnee.getId()) != null) {
                return null;
            } else {
                rateShop.setRate(rate);
                rateShop.setShop(shop);
                rateShop.setPawnee(pawnee);
                rateShop = rateShopRepository.saveAndFlush(rateShop);
                if (rateShop != null) {
                    List<RateShop> list = rateShopRepository.findAllByShop_Id(shopId);
                    int rateCount = 0;
                    for (RateShop item : list) {
                        rateCount += item.getRate();
                    }

                    shop.setRating((int) Math.round(rateCount * 1.0 / list.size()));
                    shopRepository.save(shop);
                    return shop.getRating();
                }
                return null;
            }
        } else return null;
    }
}
