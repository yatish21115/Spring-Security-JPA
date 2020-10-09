package com.login.springsecurityjpa.Repositories;

import com.login.springsecurityjpa.Models.Role;
import com.login.springsecurityjpa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByname(String name);
}
