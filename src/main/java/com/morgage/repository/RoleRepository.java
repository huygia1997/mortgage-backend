package com.morgage.repository;

import com.morgage.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findById(int id);
}
